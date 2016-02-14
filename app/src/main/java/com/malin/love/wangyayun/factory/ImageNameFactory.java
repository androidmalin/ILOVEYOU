package com.malin.love.wangyayun.factory;

import java.util.ArrayList;

/**
 * 类描述:获取图片文件夹类
 * 创建人:malin.myemail@gmail.com
 * 创建时间:15-11-10.
 * 备注:
 */
public class ImageNameFactory {
    private static final String FOLDER_NAME_ONE = "dongyu";

    /**
     * 获取asset目录下的文件夹的名称集合
     *
     * @return
     */
    public static ArrayList<String> getAssetImageFolderName() {
        ArrayList<String> assetsFolderNameList = new ArrayList();
        assetsFolderNameList.add(FOLDER_NAME_ONE);
        return assetsFolderNameList;
    }
}
