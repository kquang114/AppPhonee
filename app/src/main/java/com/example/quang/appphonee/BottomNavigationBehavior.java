package com.example.quang.appphonee;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import java.util.jar.Attributes;

/**
 * Created by quang on 5/13/19.
 */

public class BottomNavigationBehavior extends CoordinatorLayout.Behavior<BottomNavigationView>{

    public BottomNavigationBehavior(){
        super();
    }

    public BottomNavigationBehavior(Context context, AttributeSet attrs){
        super(context,attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, BottomNavigationView child,View dependency){
        boolean dependsOn = dependency instanceof FrameLayout;
        return dependsOn;
    }


    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull BottomNavigationView child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull BottomNavigationView child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        if(dy<0){
            showBottomView(child);
        }
        else if (dy > 0){
            hideBottomView(child);
        }
    }

    private void hideBottomView(BottomNavigationView v){
        v.animate().translationY(v.getHeight());
    }

    private void showBottomView(BottomNavigationView v){
        v.animate().translationY(0);
    }
}
