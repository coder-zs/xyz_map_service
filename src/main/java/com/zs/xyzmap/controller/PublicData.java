package com.zs.xyzmap.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author zs
 * @date 2019/10/26 9:07
 */
@RestController
@RequestMapping(value = "/xyz")
public class PublicData {

	@Value("${DATA_PATH}")
	String dataPath;

	@RequestMapping(value="/{name}/{level}/{col}/{rowExtention:.+}",method= RequestMethod.GET)
	public void xyz(@PathVariable String name, @PathVariable int level, @PathVariable int col, @PathVariable String rowExtention, HttpServletResponse response){
		String[] rowParts=rowExtention.split("\\.");
		try {
			String extension=rowParts[1];
			String path = dataPath+"/"+name+"/"+level+"/"+col+"/"+rowExtention;
			InputStream inputStream = new FileInputStream(path);
			if(inputStream==null){
				return;
			}
			BufferedImage br = ImageIO.read(inputStream);
			ImageIO.write(br, extension, response.getOutputStream());
			br.flush();
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(String.format("%s/%d/%d/%s请求出错!" ,name,level,col,rowExtention));
		}
	}
}
