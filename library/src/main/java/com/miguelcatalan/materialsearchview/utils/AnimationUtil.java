package com.miguelcatalan.materialsearchview.utils;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.DecelerateInterpolator;

/**
 * @author Miguel Catalan Bañuls
 */
public class AnimationUtil {

    public static final int ANIMATION_DURATION_SHORT = 150;
    public static final int ANIMATION_DURATION_MEDIUM = 400;
    public static final int ANIMATION_DURATION_LONG = 800;

    public interface AnimationListener {
        /**
         * @return true to override parent. Else execute Parent method
         */
        boolean onAnimationStart(View view);

        boolean onAnimationEnd(View view);

        boolean onAnimationCancel(View view);
    }

    public static void crossFadeViews(View showView, View hideView) {
        crossFadeViews(showView, hideView, ANIMATION_DURATION_SHORT);
    }

    public static void crossFadeViews(View showView, final View hideView, int duration) {
        fadeInView(showView, duration);
        fadeOutView(hideView, duration);
    }

    public static void fadeInView(View view) {
        fadeInView(view, ANIMATION_DURATION_SHORT);
    }

    public static void fadeInView(View view, int duration) {
        fadeInView(view, duration, null);
    }

    public static void fadeInView(View view, int duration, final AnimationListener listener) {
        view.setVisibility(View.VISIBLE);
        view.setAlpha(0f);
        ViewPropertyAnimatorListener vpListener = null;

        if (listener != null) {
            vpListener = new ViewPropertyAnimatorListener() {
                @Override
                public void onAnimationStart(View view) {
                    if (!listener.onAnimationStart(view)) {
                        //execute Parent MEthod
                        view.setDrawingCacheEnabled(true);
                    }
                }

                @Override
                public void onAnimationEnd(View view) {
                    if (!listener.onAnimationEnd(view)) {
                        //execute Parent MEthod
                        view.setDrawingCacheEnabled(false);
                    }
                }

                @Override
                public void onAnimationCancel(View view) {
                    if (!listener.onAnimationCancel(view)) {
                        //execute Parent MEthod
                    }
                }
            };
        }
        ViewCompat.animate(view).alpha(1f).setDuration(duration).setListener(vpListener);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void reveal(final View view, int animationDuration, final AnimationListener listener) {
        View viewRoot = view;
        int cx = (viewRoot.getRight());
        int cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;
        int finalRadius = Math.max(viewRoot.getWidth(), viewRoot.getHeight());

        Animator anim = ViewAnimationUtils.createCircularReveal(viewRoot, cx, cy, 0, finalRadius);
        viewRoot.setVisibility(View.VISIBLE);

        anim.setDuration(animationDuration);
        anim.setInterpolator(new DecelerateInterpolator());
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                  listener.onAnimationStart(view);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                listener.onAnimationEnd(view);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
               listener.onAnimationCancel(view);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        anim.start();
    }

    public static void fadeOutView(View view) {
        fadeOutView(view, ANIMATION_DURATION_SHORT);
    }

    public static void fadeOutView(View view, int duration) {
        fadeOutView(view, duration, null);
    }

    public static void fadeOutView(View view, int duration, final AnimationListener listener) {
        ViewCompat.animate(view).alpha(0f).setDuration(duration).setListener(new ViewPropertyAnimatorListener() {
            @Override
            public void onAnimationStart(View view) {
                if (listener == null || !listener.onAnimationStart(view)) {
                    //execute Parent MEthod
                    view.setDrawingCacheEnabled(true);
                }
            }

            @Override
            public void onAnimationEnd(View view) {
                if (listener == null || !listener.onAnimationEnd(view)) {
                    //execute Parent MEthod
                    view.setVisibility(View.GONE);
                    //view.setAlpha(1f);
                    view.setDrawingCacheEnabled(false);
                }
            }

            @Override
            public void onAnimationCancel(View view) {
                if (listener == null || !listener.onAnimationCancel(view)) {
                    //execute Parent MEthod
                }
            }
        });
    }
}