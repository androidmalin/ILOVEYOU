package com.malin.love.wangyayun.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
/**
 * 类描述:图片处理类
 * 创建人:malin.myemail@gmail.com
 * 创建时间:15-11-11.
 * 参考项目:
 */
public class ImageUtils {

    public static Bitmap getImageBitmapFromAssetsFolderThroughImagePathName(Context context, String imagePathName, int reqWidth, int reqHeight) {
        Options opts = new Options();
        opts.inJustDecodeBounds = true;
        try {
            InputStream inputStream = context.getResources().getAssets().open(imagePathName);
            inputStream.mark(2000);
            if (inputStream == null) {
                return null;
            }
            try {
                BitmapFactory.decodeStream(inputStream, null, opts);
                inputStream.reset();
                opts.inSampleSize = calculateInSampleSiez(opts, reqWidth, reqHeight);
                opts.inJustDecodeBounds = false;
                opts.inPreferredConfig = Config.ARGB_8888;
                opts.inPurgeable = true;
                opts.inInputShareable = true;
                opts.inDither = false;
                opts.inTempStorage = new byte[1024];
                if (inputStream != null) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, opts);
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                                return null;
                            }
                        }
                        if (bitmap != null) {
                            bitmap = Bitmap.createScaledBitmap(bitmap, reqWidth, reqHeight, true);
                        }
                        return bitmap;
                    } catch (OutOfMemoryError e2) {
                        e2.printStackTrace();
                        System.gc();
                        if (inputStream == null) {
                            return null;
                        }
                        try {
                            inputStream.close();
                            return null;
                        } catch (Exception e3) {
                            e3.printStackTrace();
                            return null;
                        }
                    } catch (Exception e32) {
                        e32.printStackTrace();
                        if (inputStream == null) {
                            return null;
                        }
                        try {
                            inputStream.close();
                            return null;
                        } catch (Exception e322) {
                            e322.printStackTrace();
                            return null;
                        }
                    } catch (Throwable th) {
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (Exception e3222) {
                                e3222.printStackTrace();
                                return null;
                            }
                        }
                    }
                } else if (inputStream == null) {
                    return null;
                } else {
                    try {
                        inputStream.close();
                        return null;
                    } catch (Exception e32222) {
                        e32222.printStackTrace();
                        return null;
                    }
                }
            } catch (OutOfMemoryError e22) {
                e22.printStackTrace();
                System.gc();
                return null;
            } catch (Exception e322222) {
                e322222.printStackTrace();
                return null;
            }
        } catch (IOException e4) {
            e4.printStackTrace();
            return null;
        } catch (OutOfMemoryError e222) {
            e222.printStackTrace();
            System.gc();
            return null;
        }
        return null;
    }

    public static int calculateInSampleSiez(Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            int halfHeight = height / 2;
            int halfWidth = width / 2;
            while (halfHeight / inSampleSize > reqHeight && halfWidth / inSampleSize > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static ArrayList<String> getAssetsImageNamePathList(Context context, String folderName) {
        ArrayList<String> imagePathList = new ArrayList();
        String[] imageNameArray = getAssetsImageNameArray(context, folderName);
        if (!(imageNameArray == null || imageNameArray.length <= 0 || folderName == null || folderName.replaceAll(" ","").trim().equals(""))) {
            for (String imageName : imageNameArray) {
                imagePathList.add(new StringBuffer(folderName).append(File.separator).append(imageName).toString());
            }
        }
        return imagePathList;
    }

    private static String[] getAssetsImageNameArray(Context context, String folderName) {
        String[] imageNameArray = null;
        try {
            imageNameArray = context.getAssets().list(folderName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageNameArray;
    }
}
