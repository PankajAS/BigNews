package com.plusonesoftwares.plusonesoftwares.bignews.flip;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.view.View;

import com.plusonesoftwares.plusonesoftwares.bignews.CommonClass;
import com.plusonesoftwares.plusonesoftwares.bignews.FlipViewController;
import com.plusonesoftwares.plusonesoftwares.bignews.unit.TextureUtils;

import java.util.LinkedList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static javax.microedition.khronos.opengles.GL10.GL_AMBIENT;
import static javax.microedition.khronos.opengles.GL10.GL_COLOR_BUFFER_BIT;
import static javax.microedition.khronos.opengles.GL10.GL_DEPTH_BUFFER_BIT;
import static javax.microedition.khronos.opengles.GL10.GL_DEPTH_TEST;
import static javax.microedition.khronos.opengles.GL10.GL_LEQUAL;
import static javax.microedition.khronos.opengles.GL10.GL_LIGHT0;
import static javax.microedition.khronos.opengles.GL10.GL_LIGHTING;
import static javax.microedition.khronos.opengles.GL10.GL_MODELVIEW;
import static javax.microedition.khronos.opengles.GL10.GL_NICEST;
import static javax.microedition.khronos.opengles.GL10.GL_PERSPECTIVE_CORRECTION_HINT;
import static javax.microedition.khronos.opengles.GL10.GL_POSITION;
import static javax.microedition.khronos.opengles.GL10.GL_PROJECTION;
import static javax.microedition.khronos.opengles.GL10.GL_SMOOTH;

/**
 * Created by Plus 3 on 07-03-2017.
 */

public class FlipRenderer implements GLSurfaceView.Renderer {

    private FlipViewController flipViewController;

    private FlipCards cards;

    private boolean created = false;
    private final LinkedList<Texture> postDestroyTextures = new LinkedList<Texture>();
     Activity context;
    CommonClass clsCommon = new CommonClass();
    List<String> newsCategory;
    public FlipRenderer(FlipViewController flipViewController, Activity context, FlipCards cards) {
        this.flipViewController = flipViewController;
        this.cards = cards;
        this.context =context;
        newsCategory = clsCommon.getCatWithAdmob(clsCommon.getFollowedCategoriesLink(context, false, false));
    }

    public static float[] light0Position = {0, 0, 100f, 0f};

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);

        gl.glMatrixMode(GL_PROJECTION);
        gl.glLoadIdentity();

        float fovy = 20f;
        float eyeZ = height / 2f / (float) Math.tan(TextureUtils.d2r(fovy / 2));

        GLU.gluPerspective(gl, fovy, (float) width / (float) height, 0.5f,
                eyeZ + height / 2); //set zFar be larger than eyeZ to fix issue #5

        gl.glMatrixMode(GL_MODELVIEW);
        gl.glLoadIdentity();

        GLU.gluLookAt(gl,
                width / 2f, height / 2f, eyeZ,
                width / 2f, height / 2f, 0.0f,
                0.0f, 1.0f, 0.0f
        );

        gl.glEnable(GL_LIGHTING);
        gl.glEnable(GL_LIGHT0);

        float lightAmbient[] = new float[]{3.5f, 3.5f, 3.5f, 1f};
        gl.glLightfv(GL_LIGHT0, GL_AMBIENT, lightAmbient, 0);

        light0Position = new float[]{0, 0, eyeZ, 0f};
        gl.glLightfv(GL_LIGHT0, GL_POSITION, light0Position, 0);

        //if (AphidLog.ENABLE_DEBUG) {
        //    AphidLog.d("onSurfaceChanged: %d, %d", width, height);
        //}

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glShadeModel(GL_SMOOTH);
        gl.glClearDepthf(1.0f);
        gl.glEnable(GL_DEPTH_TEST);
        gl.glDepthFunc(GL_LEQUAL);
        gl.glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);

        created = true;

        cards.invalidateTexture();
        flipViewController.sendMessage(FlipViewController.MSG_SURFACE_CREATED);

        //if (AphidLog.ENABLE_DEBUG) {
        //    AphidLog.d("onSurfaceCreated");
        //}
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        if (cards.isVisible() && cards.isFirstDrawFinished())
            gl.glClearColor(1f, 1f, 1f, 1f);
        else
            gl.glClearColor(0f, 0f, 0f, 0f);
        gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        synchronized (postDestroyTextures) {
            for (Texture texture : postDestroyTextures) {
                texture.destroy(gl);
            }
            postDestroyTextures.clear();
        }

        cards.draw(this, gl);
    }


    public void postDestroyTexture(Texture texture) {
        synchronized (postDestroyTextures) {
            postDestroyTextures.add(texture);
        }
    }
    public void updateTexture(int frontIndex, View frontView, int backIndex, View backView) {
        if (created) {
            //setting title for home navigationbar
            if(!context.getClass().getSimpleName().equals("NewsCategoryDetails")) {
                if (frontIndex != 0 && frontIndex % 3 == 0) {
                    context.setTitle("");
                } else {
                    String title = clsCommon.getCatNameByCatId(newsCategory.get(frontIndex));
                    context.setTitle(title);
                    clsCommon.setUserPrefs(clsCommon.CategroyTitle,title,context);
                }
            }
            cards.reloadTexture(frontIndex, frontView, backIndex, backView);
            flipViewController.getSurfaceView().requestRender();
        }
    }

    public static void checkError(GL10 gl) {
        //if (AphidLog.ENABLE_DEBUG) {
        //int error = gl.glGetError();
        //if (error != 0) {
        //    throw new RuntimeException(GLU.gluErrorString(error));
        //}
    }






}
