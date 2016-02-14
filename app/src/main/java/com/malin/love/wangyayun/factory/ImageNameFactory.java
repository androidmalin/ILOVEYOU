package com.malin.love.wangyayun.factory;

import java.util.ArrayList;

public class ImageNameFactory {
    private static final String FOLDER_NAME_ONE = "dongyu";

    public static ArrayList<String> getAssetImageFolderName() {
        ArrayList<String> assetsFolderNameList = new ArrayList();
        assetsFolderNameList.add(FOLDER_NAME_ONE);
        return assetsFolderNameList;
    }
}
