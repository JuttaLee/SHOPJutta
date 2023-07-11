package ShopJutta.service;

import ShopJutta.entity.GoodImg;
import ShopJutta.repository.GoodImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
@Service
@Transactional
@RequiredArgsConstructor
public class GoodImgService {
    @Value("${goodImgLocation}")
    private String goodImgLocation;
    private final GoodImgRepository goodImgRepository;
    private final FileService fileService;

    public void saveGoodImg(GoodImg goodImg, MultipartFile goodImgFile) throws Exception {
        String originalName = goodImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        if(!StringUtils.isEmpty(originalName)){
            imgName = fileService.uploadFile(goodImgLocation, originalName, goodImgFile.getBytes());
            imgUrl = "/images/good" + imgName;
        }

        // 상품 이미지 정보 저장
        goodImg.updateGoodImg(originalName, imgName, imgUrl);
        goodImgRepository.save(goodImg);
    }

    public void updateGoodImg(Long goodImgId, MultipartFile goodImgFile) throws Exception {
        if(!goodImgFile.isEmpty()) {
            GoodImg savedGoodImg = goodImgRepository.findById(goodImgId).orElseThrow(EntityNotFoundException::new);

            // 기존 상품 이미지 파일 삭제
            if(!StringUtils.isEmpty(savedGoodImg.getGoodImgName())) {
                fileService.deleteFile(goodImgLocation + "/" + savedGoodImg.getGoodImgName());
            }
            String goodOriginalImgName = goodImgFile.getOriginalFilename();
            String goodImgName = fileService.uploadFile(goodImgLocation, goodOriginalImgName, goodImgFile.getBytes());
            String goodImgUrl = "/images/good/" + goodImgName;
            savedGoodImg.updateGoodImg(goodOriginalImgName, goodImgName, goodImgUrl);
        }
    }
}