package com.seu.cose.easytrip.activity;

import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.seu.cose.easytrip.Override.GsonPaserUtils;
import com.seu.cose.easytrip.R;
import com.seu.cose.xutils3.BaseAppCompatActivity;
import com.seu.cose.xutils3.EasyTripApplication;
import com.seu.cose.xutils3.XUtilsTools;
import com.seu.cose.xutils3.pojo.Article;
import com.seu.cose.xutils3.pojo.ArticleFile;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ContentView(R.layout.activity_article_read)
public class ArticleReadActivity extends BaseAppCompatActivity {

    private Article article;
    private String articleId;
    private static EasyTripApplication app;

    @ViewInject(R.id.author_info)
        private LinearLayout authorInfo;
    @ViewInject(R.id.author_name)
        private TextView authorName;
    @ViewInject(R.id.author_ID)
        private TextView authorId;
    @ViewInject(R.id.followButton)
        private Button followButton;
    @ViewInject(R.id.article_read)
        private TextView articleView;

    public void setArticle(Article article){
        this.article = article;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        app = (EasyTripApplication) getApplication();

        authorName.setText(app.getUserInfo().getUserName());
        authorId.setText(app.getUser().getId().toString());
        showInfo(new File(app.getAbsolutePath() + "/EasyTrip/article/" + String.valueOf(articleId) + "/text.txt"));
        changeToImage();

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showInfo(File file) {
        String str = null;
        try {
            InputStream is = new FileInputStream(file);
            InputStreamReader input = new InputStreamReader(is, "UTF-8");
            BufferedReader reader = new BufferedReader(input);
            while ((str = reader.readLine()) != null) {
                articleView.append(str);
                articleView.append("\n");
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void changeToImage(){
        String text = articleView.getText().toString();
        String regEx = "@@*##";
        Pattern pattern = Pattern.compile(regEx);
        Matcher isImage = pattern.matcher(text);
        while(isImage.find()){
            String fileName = isImage.group();
            String fileUrl = "/EasyTrip/article/" + String.valueOf(article.getId()) + "/" + fileName;
            SpannableString spannableString = new SpannableString("@@"+fileName+"##");
            ImageSpan span = new ImageSpan(getApplicationContext(), Uri.parse(fileUrl));
            spannableString.setSpan(span, isImage.start(), isImage.end(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        }

    }

}
