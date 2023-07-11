package ShopJutta.service;

import ShopJutta.dto.GoodFormDto;
import ShopJutta.dto.GoodImgDto;
import ShopJutta.dto.GoodSearchDto;
import ShopJutta.dto.MainItemDto;
import ShopJutta.entity.Good;
import ShopJutta.entity.GoodImg;
import ShopJutta.repository.GoodImgRepository;
import ShopJutta.repository.GoodRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class GoodService {
    private final GoodRepository goodRepository;
    private final GoodImgService goodImgService;
    private final GoodImgRepository goodImgRepository;

    public Long saveGood(GoodFormDto goodFormDto, List<MultipartFile> goodImgFileList) throws Exception{
        // 상품 등록
        Good good = goodFormDto.createGood();
        goodRepository.save(good);

        // 상품 이미지 등록
        for(int i = 0; i <= goodImgFileList.size(); i++) {
            GoodImg goodImg = new GoodImg();
            goodImg.setGood(good);

            if(i == 0)
                goodImg.setGoodRepImgYn("Y");
            else
                goodImg.setGoodRepImgYn("N");

            goodImgService.saveGoodImg(goodImg, goodImgFileList.get(i));
        }
        return good.getId();
    }

    @Transactional(readOnly = true)
    public GoodFormDto getGoodDtl(Long goodId) {
        List<GoodImg> goodImgList = goodImgRepository.findByGoodIdSelectByIdAsc(goodId);
        List<GoodImgDto> goodImgDtoList = new ArrayList<>();
        for(GoodImg goodImg : goodImgList) {
            GoodImgDto goodImgDto = GoodImgDto.of(goodImg);
            goodImgDtoList.add(goodImgDto);
        }

        Good good = goodRepository.findById(goodId).orElseThrow(EntityNotFoundException::new);

        GoodFormDto goodFormDto = GoodFormDto.of(good);
        goodFormDto.setGoodImgDtoList(goodImgDtoList);
        return goodFormDto;
    }

    public Long updateGood(GoodFormDto goodFormDto, List<MultipartFile> goodImgFileList) throws Exception{
        // 상품 수정
        Good good = goodRepository.findById(goodFormDto.getId()).orElseThrow(EntityNotFoundException::new);
        good.updateGood(goodFormDto);
        List<Long> goodImgIds = goodFormDto.getGoodImgIds();

        // 상품 이미지 등록
        for (int g=0; g<goodImgFileList.size(); g++) {
            goodImgService.updateGoodImg(goodImgIds.get(g), goodImgFileList.get(g));
        }
        return good.getId();
    }

    @Transactional(readOnly = true)
    public Page<Good> getAdminGoodPage(GoodSearchDto goodSearchDto, Pageable pageable) {
        return goodRepository.getAdminGoodPage(goodSearchDto, pageable);
    }

    @Transactional(readOnly = true)
    public Page<MainItemDto> getMainItemPage(GoodSearchDto goodSearchDto, Pageable pageable) {
        return goodRepository.getMainItemPage(goodSearchDto, pageable);
    }
}