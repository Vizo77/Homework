package com.example.vizo.homework;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.SparseArray;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class TextRecognizerTest {
    Context appContext = InstrumentationRegistry.getTargetContext();
    TextRecognizer textRecognizer;

    @Before
    public void prepare() {
        textRecognizer = new TextRecognizer.Builder(appContext).build();
    }

    @After
    public void release() {
        textRecognizer.release();
    }
    /**
     * Тест проверяет простейшую картинку с английским предложением.
     **/
    @Test
    public void shouldRecognizeTextEng() {
        String expected = "Many people who speak English";
        String actual = recognizeTextFrom(R.drawable.eng);
        assertEquals("Doesn't recognized simple image",expected, actual);
    }
    /**
     * Тест проверяет простейшую картинку с русским текстом.
     **/
    @Test
    public void shouldRecognizeTextRus() {
        String expected = "Идея состоит в том, чтобы писать тесты для каждой нетривиальной функции или метода.";
        String actual=recognizeTextFrom(R.drawable.rus);
        assertEquals("Russian not implemented yet",expected, actual);
    }
    /**
     * Тест проверяет картинку низкого разрешения.\
     **/
    @Test
    public void  shouldRecognizeTextLowRes() {
        String expected = "Front and side cab area/top of rear frame.";
        String actual=recognizeTextFrom(R.drawable.lowres);
        assertEquals("Low resolution image doesn't recognize",expected, actual);
    }

    private String recognizeTextFrom(int resId) {
        String actual = "";
        Bitmap bitmap = BitmapFactory.decodeResource(appContext.getResources(), resId);
        Frame imageFrame = new Frame.Builder()
                .setBitmap(bitmap)
                .build();
        SparseArray<TextBlock> textBlock = textRecognizer.detect(imageFrame);

        for (int i = 0; i < textBlock.size(); i++) {
            TextBlock blocks = textBlock.get(textBlock.keyAt(i));
            actual=actual + blocks.getValue();
        }
        return actual;
    }

}
