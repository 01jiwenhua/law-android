package com.shx.lawwh.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;


import com.shx.lawwh.R;
import com.shx.lawwh.common.LogGloble;
import com.shx.lawwh.common.SystemConfig;
import com.shx.lawwh.libs.dialog.ToastUtil;

import java.util.List;

/**
 * 提交校验工具类
 *
 * @author xby
 *         <p/>
 *         2013-10-10
 */
public class RegexpUtils {

    /**
     * 校验文本编辑框
     *
     * @param context
     * @param editTexts
     * @return
     */
    public static boolean regexEdttext(@NonNull Context context, @NonNull EditText... editTexts) {
        for (EditText edt : editTexts) {
            if (edt.getTag() == null) {
                if (SystemConfig.LOGFLAG)
                    ToastUtil.getInstance().toastInCenter(context, "请开发人员填写校验规则Tag");
                return false;
            } else {
                //校验内容
                String tag = edt.getTag().toString();
                if (tag.contains("canBeNull")) {//选填项目 只有在不为空的时候校验内容
                    if (!TextUtils.isEmpty(edt.getText().toString().trim())) {
                        if (regexContent(context, edt, tag)) return false;
                    }
                } else {//必填
                    if (TextUtils.isEmpty(edt.getText().toString().trim())) {
//                        ToastUtil.getInstance().toastInCenter(context, edt.getHint().toString());
//                        edt.setHintTextColor(context.getResources().getColor(R.color.notInputHintColor));
//                        edt.requestFocus();
                        return false;
                    } else {
                        edt.setHintTextColor(context.getResources().getColor(R.color.common_EditText_color));
                    }
                    if (regexContent(context, edt, tag)) return false;
                }
            }
        }
        return true;
    }

    /**
     * 校验内容
     *
     * @param context
     * @param edt
     * @param tag
     * @return
     */
    private static boolean regexContent(@NonNull Context context, @NonNull EditText edt, @NonNull String tag) {
        String[] regs = tag.split("\\|\\|");
        LogGloble.d("info", "length =  " + regs.length);
        for (int i = 0; i < regs.length; i++) {
            LogGloble.d("info", "regs[ " + i + "] =  " + regs[i]);
        }
        String content = edt.getText().toString().trim();
        if (context.getResources().getString(R.string.back_card).equals(tag)) {
            content = content.replaceAll(" ", "");
        }
        if (!content.matches(regs[0])) {
            ToastUtil.getInstance().toastInCenter(context, regs[1]);
            edt.setHintTextColor(context.getResources().getColor(R.color.notInputHintColor));
            edt.requestFocus();
            return true;
        } else {
            edt.setHintTextColor(context.getResources().getColor(R.color.common_EditText_color));
        }
        return false;
    }

    /**
     * 校验选择器和文本编辑框
     *
     * @param context
     * @param textViews
     * @param editTexts
     * @return
     */
    public static boolean regexTextViewAndEdttext(@NonNull Context context, @NonNull List<TextView> textViews, EditText... editTexts) {

        if (!regexEdttext(context, editTexts)) {
            return false;
        }
        for (TextView tv : textViews) {
            if (tv.getHint().toString().trim().equals("请选择") || ("请选择").equals(tv.getText().toString().trim())) {
                ToastUtil.getInstance().toastInCenter(context, tv.getTag().toString().trim());
                return false;
            }
        }
        return true;
    }

    public static boolean regexEdttext(@NonNull Context context, @NonNull List<TextView> textViews, @NonNull List<EditText> editTexts) {
        for (int i = 0; i < textViews.size(); i++) {
            if (textViews.get(i).getHint().toString().trim().equals("请选择")) {
                ToastUtil.getInstance().toastInCenter(context, textViews.get(i).getTag().toString().trim());
                return false;
            }
            if (TextUtils.isEmpty(editTexts.get(i).getText().toString().trim())) {
                ToastUtil.getInstance().toastInCenter(context, editTexts.get(i).getHint().toString());
                editTexts.get(i).setHintTextColor(context.getResources().getColor(R.color.notInputHintColor));
                editTexts.get(i).requestFocus();
                return false;
            } else {
                editTexts.get(i).setHintTextColor(context.getResources().getColor(R.color.common_EditText_color));
            }
        }
        return true;
    }

}
