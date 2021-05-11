/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gesforback.serverfile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import java.io.IOException;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 *
 * @author sud
 */

@Component
public class FlashCloudinary {

    private Map configOpenCloudinary() {
        Map chave = ObjectUtils.asMap("cloud_name", "hgefhowox","api_key", "621959836883296","api_secret", "LT3_ARgtuOtxM61l5QtXceT2S14");
        return chave;
    }

    public Map savePhotoThumbnail(byte[] dataImage, String residentName, Long idResident) throws IOException {
        Map conf = ObjectUtils.asMap("public_id", "resident/" + residentName + "_" + idResident, "transformation", new Transformation().width(50).height(50).gravity("face").crop("fill").radius("max"));
        Cloudinary cloudinary = new Cloudinary(configOpenCloudinary());
        cloudinary.url().transformation(new Transformation().aspectRatio("1").radius("max").crop("fill")).imageTag("white_chicken.png");
        return cloudinary.uploader().upload(dataImage, conf);//.gravity("face")
    }
    
    
}
