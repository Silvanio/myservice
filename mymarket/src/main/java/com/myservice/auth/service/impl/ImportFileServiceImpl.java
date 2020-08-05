package com.myservice.auth.service.impl;

import com.myservice.auth.model.*;
import com.myservice.auth.model.dto.ImportSectorDTO;
import com.myservice.auth.model.dto.ImportStockDTO;
import com.myservice.auth.repository.*;
import com.myservice.auth.service.ImportFileService;
import com.myservice.common.exceptions.BusinessException;
import com.myservice.common.exceptions.MessageException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ImportFileServiceImpl implements ImportFileService {

    private static final Logger logger = LoggerFactory.getLogger(ImportFileServiceImpl.class);

    @Autowired
    ProductRepository productRepository;

    @Autowired
    StockRepository stockRepository;

    @Autowired
    SegmentRepository segmentRepository;

    @Autowired
    SectorRepository sectorRepository;

    @Autowired
    SubSectorRepository subSectorRepository;


    @Override
    public void uploadSegmentFII(MultipartFile file) {
        logger.info("## - Start LOG uploadSegmentFII");
        validateEmptyFile(file);
        try {
            byte[] bytes = file.getBytes();
            InputStream targetStream = new ByteArrayInputStream(bytes);
            Workbook workbook = new XSSFWorkbook(targetStream);
            Sheet sheet = workbook.getSheetAt(0);

            SubSector subSector = subSectorRepository.findByName("Outros Títulos");

            for (Row row : sheet) {
                String stockCell = row.getCell(0).getStringCellValue();
                String segmentCell = row.getCell(1).getStringCellValue();
                logger.info("## - Stock: " + stockCell);

                Stock stock = stockRepository.findByCode(stockCell);
                if (stock != null) {
                    Segment segment = segmentRepository.findByName(segmentCell);
                    if (segment == null) {
                        segment = new Segment();
                        segment.setName(segmentCell);
                        segment.setSubSector(subSector);
                        segmentRepository.save(segment);
                    }
                    stock.setSegment(segment);
                    stockRepository.save(stock);
                }

                logger.info("## - FIM Stock: " + stockCell);

            }
        } catch (Exception e) {
            logger.info("## - ERRO LOG uploadSegmentFII");
            throw new BusinessException(MessageException.MSG_GENERAL_ERROR.getMessage(), MessageException.MSG_GENERAL_UPLOAD_ERROR.getMessage());

        }


    }

    @Override
    public void uploadSectors(MultipartFile file) {

        logger.info("## - Start LOG uploadSectors");

        validateEmptyFile(file);
        try {
            List<ImportSectorDTO> sectorDTOList = getListSector(file);

            for (ImportSectorDTO sectorDTO : sectorDTOList) {

                Sector sector = sectorRepository.findByName(sectorDTO.getSector());
                if (sector == null) {
                    sector = new Sector();
                    sector.setName(sectorDTO.getSector());
                    sector = sectorRepository.save(sector);
                }

                logger.info("## - Sector: " + sector.getName());

                SubSector subSector = subSectorRepository.findByName(sectorDTO.getSubSector());
                if (subSector == null) {
                    subSector = new SubSector();
                    subSector.setName(sectorDTO.getSubSector());
                    subSector.setSector(sector);
                    subSector = subSectorRepository.save(subSector);
                }

                logger.info("## - Sector: " + subSector.getName());

                Segment segment = segmentRepository.findByName(sectorDTO.getSegment());
                if (segment == null) {
                    segment = new Segment();
                    segment.setName(sectorDTO.getSegment());
                    segment.setSubSector(subSector);
                    segment = segmentRepository.save(segment);
                }

                logger.info("## - Segment: " + segment.getName());

                if (sectorDTO.getStocks() != null && !sectorDTO.getStocks().isEmpty()) {
                    for (String stock : sectorDTO.getStocks()) {
                        List<Stock> stocksBD = stockRepository.findByCodeIgnoreCaseContaining(stock);
                        if (stocksBD != null && !stocksBD.isEmpty()) {
                            for (Stock stockBD : stocksBD) {
                                stockBD.setSegment(segment);
                                logger.info("## - Save Stock: " + stockBD.getName());
                                stockRepository.save(stockBD);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            logger.info("## - ERRO LOG uploadSectors");
            throw new BusinessException(MessageException.MSG_GENERAL_ERROR.getMessage(), MessageException.MSG_GENERAL_UPLOAD_ERROR.getMessage());
        }
        logger.info("## - FIM LOG uploadSectors");
    }


    @Override
    public void uploadStocks(MultipartFile file) {
        logger.info("## - Start LOG uploadStocks");

        validateEmptyFile(file);
        Reader targetReader = null;
        try {
            String[] HEADERS = {"RptDt", "TckrSymb", "Asst", "AsstDesc", "SgmtNm", "MktNm",
                    "SctyCtgyNm", "XprtnDt", "XprtnCd", "TradgStartDt", "TradgEndDt", "BaseCd", "ConvsCritNm", "MtrtyDtTrgtPt",
                    "ReqrdConvsInd", "ISIN", "CFICd", "DlvryNtceStartDt", "DlvryNtceEndDt", "OptnTp", "CtrctMltplr", "AsstQtnQty",
                    "AllcnRndLot", "TradgCcy", "DlvryTpNm", "WdrwlDays", "WrkgDays", "ClnrDays", "RlvrBasePricNm", "OpngFutrPosDay",
                    "SdTpCd1", "UndrlygTckrSymb1", "SdTpCd2", "UndrlygTckrSymb2", "PureGoldWght", "ExrcPric", "OptnStyle", "ValTpNm", "PrmUpfrntInd",
                    "OpngPosLmtDt", "DstrbtnId", "PricFctr", "DaysToSttlm", "SrsTpNm", "PrtcnFlg", "AutomtcExrcInd", "SpcfctnCd", "CrpnNm",
                    "CorpActnStartDt", "CtdyTrtmntTpNm", "MktCptlstn", "CorpGovnLvlNm"};

            targetReader = new InputStreamReader(file.getInputStream(), "ISO_8859_1");

            Iterable<CSVRecord> records = CSVFormat.newFormat(';')
                    .withHeader(HEADERS)
                    .withFirstRecordAsHeader()
                    .parse(targetReader);

            fillStocks(records);

            targetReader.close();

        } catch (IOException e) {
            if (targetReader != null) {
                try {
                    targetReader.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
            throw new BusinessException(MessageException.MSG_GENERAL_ERROR.getMessage(), MessageException.MSG_GENERAL_UPLOAD_ERROR.getMessage());
        }
        logger.info("## - FIM LOG uploadStocks");
    }

    private List<ImportSectorDTO> getListSector(MultipartFile file) throws IOException {

        byte[] bytes = file.getBytes();

        InputStream targetStream = new ByteArrayInputStream(bytes);
        Workbook workbook = new XSSFWorkbook(targetStream);
        Sheet sheet = workbook.getSheetAt(0);

        List<ImportSectorDTO> sectorDTOList = new ArrayList<ImportSectorDTO>();
        ImportSectorDTO last = new ImportSectorDTO();
        ImportSectorDTO current = null;

        for (Row row : sheet) {
            String sector = row.getCell(0).getStringCellValue();
            String subSector = row.getCell(1).getStringCellValue();
            String segment = row.getCell(2).getStringCellValue();
            String stock = row.getCell(3).getStringCellValue();

            if (stock == null || stock.isBlank()) {
                if (subSector == null || subSector.isBlank()) {
                    subSector = last.getSubSector();
                }
                if (sector == null || sector.isBlank()) {
                    sector = last.getSector();
                }
                current = new ImportSectorDTO();
                current.setStocks(new ArrayList<String>());
                current.setSegment(segment);
                current.setSubSector(subSector);
                current.setSector(sector);
                sectorDTOList.add(current);
                last = current;
            } else {
                current.getStocks().add(stock);
            }
        }
        return sectorDTOList;
    }

    private void fillStocks(Iterable<CSVRecord> records) {

        Product action = productRepository.findByCode("STOCK");
        Product founds = productRepository.findByCode("FUNDS");
        Product etf = productRepository.findByCode("ETF");
        Product bdr = productRepository.findByCode("BDR");
        Product index = productRepository.findByCode("INDEX");
        Product fii = productRepository.findByCode("FII");


        for (CSVRecord record : records) {

            ImportStockDTO importStockDTO = fillImportStockDTO(record);

            Stock stock = null;

            if (isAction(importStockDTO)) {
                stock = new Stock();
                stock.setProduct(action);
                stock.setCode(importStockDTO.getCode());
            }
            if (isFounds(importStockDTO)) {
                stock = new Stock();
                stock.setProduct(founds);
                stock.setCode(importStockDTO.getCode());
                if (isFII(importStockDTO.getNome())) {
                    stock.setProduct(fii);
                }
            }
            if (isETF(importStockDTO)) {
                stock = new Stock();
                stock.setProduct(etf);
                stock.setCode(importStockDTO.getCode());
            }
            if (isBDR(importStockDTO)) {
                stock = new Stock();
                stock.setProduct(bdr);
                stock.setCode(importStockDTO.getCode());
            }
            if (isINDEX(importStockDTO)) {
                stock = new Stock();
                stock.setProduct(index);
                stock.setCode(importStockDTO.getSmallCode());
            }

            if (stock != null) {
                stock.setName(importStockDTO.getNome());
                stock.setIsin(importStockDTO.getIsin());
                stock.setSpecificationCode(importStockDTO.getSpecificationCode());
                stockRepository.save(stock);
            }
        }
    }

    private boolean isAction(ImportStockDTO importStockDTO) {
        String product = importStockDTO.getProduct();
        String segment = importStockDTO.getSegment();
        return ((product.contains("SHARES") || product.contains("UNIT") || product.contains("WARRANT"))
                && "CASH".equalsIgnoreCase(segment));
    }

    private boolean isFounds(ImportStockDTO importStockDTO) {
        String product = importStockDTO.getProduct();
        String segment = importStockDTO.getSegment();
        return (product.contains("FUNDS") && "CASH".equalsIgnoreCase(segment));
    }

    private boolean isETF(ImportStockDTO importStockDTO) {
        String product = importStockDTO.getProduct();
        String segment = importStockDTO.getSegment();
        return (product.contains("ETF ") && "CASH".equalsIgnoreCase(segment));
    }

    private boolean isBDR(ImportStockDTO importStockDTO) {
        String product = importStockDTO.getProduct();
        String segment = importStockDTO.getSegment();
        return (product.contains("BDR") && "CASH".equalsIgnoreCase(segment));
    }

    private boolean isINDEX(ImportStockDTO importStockDTO) {
        String product = importStockDTO.getProduct();
        String segment = importStockDTO.getSegment();
        return ((product.contains("INDEX") && !product.contains("ETF")) && "CASH".equalsIgnoreCase(segment));
    }

    private boolean isFII(String name) {
        name = name.toUpperCase();
        return (name.contains("FII") || name.contains("IMOB") || name.contains("IMOBILIARIO") || name.contains("IMOBILIÁRIO"));
    }

    private ImportStockDTO fillImportStockDTO(CSVRecord record) {
        ImportStockDTO importStockDTO = new ImportStockDTO();
        importStockDTO.setCode(record.get("TckrSymb"));
        importStockDTO.setSegment(record.get("SgmtNm"));
        importStockDTO.setProduct(record.get("SctyCtgyNm"));
        importStockDTO.setIsin(record.get("ISIN"));
        importStockDTO.setSpecificationCode(record.get("SpcfctnCd"));
        importStockDTO.setNome(record.get("CrpnNm"));
        importStockDTO.setSmallCode(record.get("Asst"));
        return importStockDTO;
    }

    private void validateEmptyFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new BusinessException(MessageException.MSG_GENERAL_ERROR.getMessage(), MessageException.MSG_GENERAL_UPLOAD_ERROR.getMessage());
        }
    }
}
