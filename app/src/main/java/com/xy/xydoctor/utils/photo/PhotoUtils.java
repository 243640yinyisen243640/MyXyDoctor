package com.xy.xydoctor.utils.photo;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.UriUtils;

import java.io.File;

/**
 * 描述:  拍照及选择照片工具类
 * 作者: LYD
 * 创建日期: 2020/1/18 11:59
 */
public class PhotoUtils {
    public static final int TAKE_PHOTO = 10001;
    public static final int SELECT_PHOTO = 10002;
    public static final int CROP_PHOTO = 10003;
    OnSelectListener mListener;
    private Activity mActivity;
    private Fragment mFragment;
    //拍照或剪切后图片的存放位置
    private String imgPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + System.currentTimeMillis() + ".jpg";
    //是否要裁剪（默认不裁剪）
    private boolean mShouldCrop = false;
    private Uri mOutputUri = null;
    private File mInputFile;
    private File mOutputFile = null;
    //剪裁图片宽高比
    private int mAspectX = 1;
    private int mAspectY = 1;
    //剪裁图片大小
    private int mOutputX = 500;
    private int mOutputY = 500;

    private PhotoUtils() {
    }

    public static PhotoUtils getInstance() {
        return Holder.instance;
    }

    /**
     * 可指定是否在拍照或从图库选取照片后进行裁剪
     * <p>
     * 默认裁剪比例1:1，宽度为500，高度为500
     *
     * @param shouldCrop 是否裁剪
     * @param activity   上下文
     * @param listener   选取图片监听
     */
    public void init(Activity activity, boolean shouldCrop, OnSelectListener listener) {
        mActivity = activity;
        mListener = listener;
        mShouldCrop = shouldCrop;
        imgPath = generateImagePath();
    }

    /**
     * 可指定是否在拍照或从图库选取照片后进行裁剪
     * <p>
     * 默认裁剪比例1:1，宽度为500，高度为500
     *
     * @param shouldCrop 是否裁剪
     * @param fragment   上下文
     * @param listener   选取图片监听
     */
    public void init(Fragment fragment, boolean shouldCrop, OnSelectListener listener) {
        mFragment = fragment;
        mListener = listener;
        mShouldCrop = shouldCrop;
        imgPath = generateFragmentImagePath();
    }

    /**
     * 可以拍照或从图库选取照片后裁剪的比例及宽高
     *
     * @param activity 上下文
     * @param aspectX  图片裁剪时的宽度比例
     * @param aspectY  图片裁剪时的高度比例
     * @param outputX  图片裁剪后的宽度
     * @param outputY  图片裁剪后的高度
     * @param listener 选取图片监听
     */
    public void initParm(Activity activity, int aspectX, int aspectY, int outputX, int outputY, OnSelectListener listener) {
        mActivity = activity;
        mListener = listener;
        mShouldCrop = true;
        imgPath = generateImagePath();
        mAspectX = aspectX;
        mAspectY = aspectY;
        mOutputX = outputX;
        mOutputY = outputY;
    }

    /**
     * 拍照获取
     */
    public void takePhoto(Activity activity) {
        File imgFile = new File(imgPath);
        if (!imgFile.getParentFile().exists()) {
            imgFile.getParentFile().mkdirs();
        }
        Uri imgUri = UriUtils.file2Uri(imgFile);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
        activity.startActivityForResult(intent, TAKE_PHOTO);
    }

    /**
     * 拍照获取
     */
    public void takePhoto(Fragment fragment) {
        File imgFile = new File(imgPath);
        if (!imgFile.getParentFile().exists()) {
            imgFile.getParentFile().mkdirs();
        }
        Uri imgUri = UriUtils.file2Uri(imgFile);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
        fragment.startActivityForResult(intent, TAKE_PHOTO);
    }

    /**
     * 从图库获取
     */
    public void selectPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        mActivity.startActivityForResult(intent, SELECT_PHOTO);
    }

    /**
     * 裁剪照片
     *
     * @param inputFile
     * @param outputFile
     */
    private void zoomPhoto(File inputFile, File outputFile) {
        File parentFile = outputFile.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setDataAndType(getImageContentUri(mActivity, inputFile), "image/*");
        } else {
            intent.setDataAndType(Uri.fromFile(inputFile), "image/*");
        }
        intent.putExtra("crop", "true");
        //设置剪裁框宽高比
        intent.putExtra("aspectX", mAspectX);
        intent.putExtra("aspectY", mAspectY);
        //设置剪裁图片大小(图片质量的大小)
        intent.putExtra("outputX", mOutputX);
        intent.putExtra("outputY", mOutputY);
        // 是否返回uri
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outputFile));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        mActivity.startActivityForResult(intent, CROP_PHOTO);
    }

    public void bindForResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PhotoUtils.TAKE_PHOTO://拍照
                    mInputFile = new File(imgPath);
                    if (mShouldCrop) {
                        mOutputFile = new File(generateImagePath());
                        mOutputUri = Uri.fromFile(mOutputFile);
                        zoomPhoto(mInputFile, mOutputFile);
                    } else {
                        mOutputUri = Uri.fromFile(mInputFile);
                        if (mListener != null) {
                            mListener.onFinish(mInputFile, mOutputUri);
                        }
                    }
                    break;
                case PhotoUtils.SELECT_PHOTO://图库
                    if (data != null) {
                        Uri uri = data.getData();
                        String imgPath = PhotoHelper.getPath(mActivity, uri);  // 获取图片路径的方法调用
                        mInputFile = new File(imgPath);
                        if (mShouldCrop) {
                            mOutputFile = new File(generateImagePath());
                            mOutputUri = Uri.fromFile(mOutputFile);
                            zoomPhoto(mInputFile, mOutputFile);
                        } else {
                            mOutputUri = Uri.fromFile(mInputFile);
                            if (mListener != null) {
                                mListener.onFinish(mInputFile, mOutputUri);
                            }
                        }
                    }
                    break;
                case PhotoUtils.CROP_PHOTO://裁剪
                    if (data != null) {
                        if (mOutputUri != null) {
                            //删除拍照的临时照片
                            File tmpFile = new File(imgPath);
                            if (tmpFile.exists())
                                tmpFile.delete();
                            if (mListener != null) {
                                mListener.onFinish(mOutputFile, mOutputUri);
                            }
                        }
                    }
                    break;
            }
        }
    }

    /**
     * 安卓7.0裁剪根据文件路径获取uri
     */
    private Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    /**
     * 产生图片的路径，带文件夹和文件名，文件名为当前毫秒数
     */
    private String generateImagePath() {
        return getExternalStoragePath() + File.separator + System.currentTimeMillis() + ".jpg";
    }

    private String generateFragmentImagePath() {
        return getFragmentExternalStoragePath() + File.separator + System.currentTimeMillis() + ".jpg";
    }

    /**
     * 获取SD下的应用目录
     */
    private String getExternalStoragePath() {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsolutePath());
        sb.append(File.separator);
        String ROOT_DIR = "Android/data/" + mActivity.getPackageName();
        sb.append(ROOT_DIR);
        sb.append(File.separator);
        return sb.toString();
    }

    /**
     * 获取SD下的应用目录
     */
    private String getFragmentExternalStoragePath() {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsolutePath());
        sb.append(File.separator);
        String ROOT_DIR = "Android/data/" + mFragment.getActivity().getPackageName();
        sb.append(ROOT_DIR);
        sb.append(File.separator);
        return sb.toString();
    }


    public interface OnSelectListener {
        void onFinish(File outputFile, Uri outputUri);
    }

    private static class Holder {
        private static final PhotoUtils instance = new PhotoUtils();
    }


}
