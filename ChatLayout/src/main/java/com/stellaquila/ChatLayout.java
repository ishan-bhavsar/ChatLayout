package com.stellaquila;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by Ishan Bhavsar on 09-02-2018.
 */

public class ChatLayout extends LinearLayout {

    private int mForegroundColor;
    private int mBackgroundColor;
    private float mCornerRadius;
    private float mTriangleCornerRadius;
    private float mTriangleLength;
    private float mTriangleAngle;
    private boolean mSender;
    private float mMargin;
    private float mPadding;
    private int mLayoutWidth;
    private int mLayoutHeight;
    private ArrayList<Point> mOriginList;
    private Point mOrigin;
    private int mStyle;

    private static final String TAG = ChatLayout.class.getSimpleName();
    public static final int STYLE_WHATSAPP = 1;
    public static final int STYLE_IPHONE = 2;

    public ChatLayout(Context context) {
        super(context);

        mOriginList = new ArrayList<>();
        mPadding = 5;
        mMargin = 5;
        mCornerRadius = 10;
        mTriangleCornerRadius = 2;
        mTriangleLength = 10;
        mTriangleAngle = 45;
        mForegroundColor = getResources().getColor(R.color.primary_material_light);
        mBackgroundColor = Color.TRANSPARENT;
        mSender = true;

        if (getBackground() == null) {
            setBackgroundColor(Color.TRANSPARENT);
        }

        if (isInEditMode())
            return;
    }

    public ChatLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        mOriginList = new ArrayList<>();

        TypedArray customAttr = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ChatLayout,
                0, 0);

        try {
            mPadding = customAttr.getDimensionPixelSize(R.styleable.ChatLayout_padding, 5);
            mMargin = customAttr.getDimensionPixelSize(R.styleable.ChatLayout_margin, 5);
            mCornerRadius = customAttr.getDimensionPixelSize(R.styleable.ChatLayout_corner_radius, 10);
            mTriangleCornerRadius = customAttr.getDimensionPixelSize(R.styleable.ChatLayout_triangle_corner_radius, 2);
            mTriangleLength = customAttr.getDimensionPixelSize(R.styleable.ChatLayout_triangle_length, 10);
            mTriangleAngle = customAttr.getFloat(R.styleable.ChatLayout_triangle_angle, 45);
            mForegroundColor = customAttr.getColor(R.styleable.ChatLayout_layout_foreground, getResources().getColor(R.color.primary_material_light));
            mBackgroundColor = customAttr.getColor(R.styleable.ChatLayout_layout_background, Color.TRANSPARENT);
            mSender = customAttr.getBoolean(R.styleable.ChatLayout_sender, true);
            mStyle = customAttr.getInt(R.styleable.ChatLayout_style, STYLE_WHATSAPP);
        } finally {
            customAttr.recycle();
        }

        if (getBackground() == null)
            setBackgroundColor(Color.TRANSPARENT);

        if (isInEditMode())
            return;
    }

    public ChatLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mOriginList = new ArrayList<>();

        TypedArray customAttr = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ChatLayout,
                0, 0);

        try {
            mPadding = customAttr.getDimensionPixelSize(R.styleable.ChatLayout_padding, 5);
            mMargin = customAttr.getDimensionPixelSize(R.styleable.ChatLayout_margin, 5);
            mCornerRadius = customAttr.getDimensionPixelSize(R.styleable.ChatLayout_corner_radius, 10);
            mTriangleCornerRadius = customAttr.getDimensionPixelSize(R.styleable.ChatLayout_triangle_corner_radius, 2);
            mTriangleLength = customAttr.getDimensionPixelSize(R.styleable.ChatLayout_triangle_length, 10);
            mTriangleAngle = customAttr.getFloat(R.styleable.ChatLayout_triangle_angle, 45);
            mForegroundColor = customAttr.getColor(R.styleable.ChatLayout_layout_foreground, getResources().getColor(R.color.primary_material_light));
            mBackgroundColor = customAttr.getColor(R.styleable.ChatLayout_layout_background, Color.TRANSPARENT);
            mSender = customAttr.getBoolean(R.styleable.ChatLayout_sender, true);
            mStyle = customAttr.getInt(R.styleable.ChatLayout_style, STYLE_WHATSAPP);
        } finally {
            customAttr.recycle();
        }

        if (getBackground() == null)
            setBackgroundColor(Color.TRANSPARENT);

        if (isInEditMode())
            return;
    }

    @Override
    public void draw(Canvas canvas) {

        int width = getWidth();
        int height = getHeight();

        if (mCornerRadius > width / 2) mCornerRadius = width / 2;
        if (mCornerRadius > height / 2) mCornerRadius = height / 2;
        if (mTriangleCornerRadius > 0.2f * mTriangleLength)
            mTriangleCornerRadius = 0.2f * mTriangleLength;

        //Choose layout
        Paint p = new Paint();
        p.setAntiAlias(true);

        Path path;
        path = getBackground(width, height);
        p.setColor(mBackgroundColor);
        canvas.drawPath(path, p);

        if (mSender)
            path = getSender(width, height, mStyle);
        else
            path = getReceiver(width, height, mStyle);

        p.setColor(mForegroundColor);
        canvas.drawPath(path, p);

        super.draw(canvas);
    }

    @NonNull
    /**
     * Return Path for sender Chat Bubble
     */
    private Path getBackground(int width, int height) {
        Path path = new Path();
        path.lineTo(width, 0);                                                 // Top Line
        path.lineTo(width, height);                                                // Right Line
        path.lineTo(0, height);                                               // Bottom Line
        path.lineTo(0, 0);                                               // Left Line
        path.close();

        return path;
    }


    @NonNull
    /**
     * Return Path for receiver Chat Bubble
     */
    private Path getReceiver(int width, int height, int style) {

        float m = mMargin;                      // min Margin

        float tr = mTriangleCornerRadius;
        float rx = mCornerRadius;
        float ry = mCornerRadius;
        /*
        *
        *              Triangle Length
        *           @45  _______
        *               \      |
        *                \     |
        *   Hypotenuse    \    |    Triangle Height = Triangle length * Tan 30
        *                  \   |
        *                   \  |
        *                    \ |
        *                     \|
        * */
        float tL1 = mTriangleLength;                                                                // Triangle Length
        float tH1 = (float) (mTriangleLength * Math.tan(Math.toRadians(mTriangleAngle)));           // Triangle Height
        float tL2 = (float) (2 * mTriangleCornerRadius / Math.tan(Math.toRadians(mTriangleAngle))); // Triangle Length
        float tH2 = 2 * mTriangleCornerRadius;                                                      // Triangle Height

        Path path = new Path();

        switch (style) {
            case STYLE_WHATSAPP: {
                float rT = width - rx - tL2 - (2 * m);                   // Rectangle Top
                float rB = width - tL2 - tL1 - (2 * rx) - (2 * m);       // Rectangle Bottom
                float rR = height - (2 * ry) - (2 * m);                  // Rectangle Right
                float rL = height - tH1 - ry - (2 * m);                  // Rectangle Left

                path.moveTo(m + tL2, m);                              // Start at
                path.rLineTo(rT, 0);                                 // Top Line
                path.rQuadTo(rx, 0, rx, ry);                        // Top Right corner
                path.rLineTo(0, rR);                                 // Right Line
                path.rQuadTo(0, ry, -rx, ry);                       // Bottom Right Corner
                path.rLineTo(-rB, 0);                                // Bottom Line
                path.rQuadTo(-rx, 0, -rx, -ry);                     // Bottom Left Corner
                path.rLineTo(0, -rL);                                // Left Line
                path.rLineTo(-(tL1), -(tH1 - tH2));                      // Triangle Slant Line
                path.rQuadTo(-tL2, -tr, 0, -tH2);                   // Top Left Corner
                path.close();
            }
            break;
            case STYLE_IPHONE: {
                float p = 5;                                             // min Padding
                float rT = width - tL1 - (2 * rx) - (2 * m) + p;         // Rectangle Top
                float rB = width - tL1 - (2 * rx) - (2 * m) + p;         // Rectangle Bottom
                float rR = height - (2 * ry) - (2 * m);                  // Rectangle Right
                float rL = height - (2.5f * ry) - (2 * m);                 // Rectangle Left

                path.moveTo(m + tL1 + rx - p, m);                                     // Start at
                path.rLineTo(rT, 0);                                                 // Top Line
                path.rQuadTo(rx, 0, rx, ry);                                        // Top Right corner
                path.rLineTo(0, rR);                                                 // Right Line
                path.rQuadTo(0, ry, -rx, ry);                                       // Bottom Right Corner
                path.rLineTo(-rB, 0);                                                // Bottom Line
                path.rQuadTo(-rx / 2, 0, -rx / 2, -ry / 2);                // Bottom Left Corner
                path.rQuadTo(-((rx / 2) + tL1) / 2, ry / 2, -((rx / 2) + tL1), ry / 2); // Bottom curve Bottom
                path.rQuadTo(((rx / 2) + tL1) / 2, 0, ((rx / 2) + tL1), -ry * 1.5f);  // Bottom curve Top
                path.rLineTo(0, -rL);                                                // Left Line
                path.rQuadTo(0, -ry, rx, -ry);                                      // Top Left Corner
                path.close();
            }
            break;
        }
        return path;
    }

    @NonNull
    /**
     * Return Path for sender Chat Bubble
     */
    private Path getSender(int width, int height, int style) {

        float m = mMargin;                      // min Margin
        float tr = mTriangleCornerRadius;
        float rx = mCornerRadius;
        float ry = mCornerRadius;
        /*
        *
        *              Triangle Length
        *           @45  _______
        *               \      |
        *                \     |
        *   Hypotenuse    \    |    Triangle Height = Triangle length * Tan 30
        *                  \   |
        *                   \  |
        *                    \ |
        *                     \|
        * */
        float tL1 = mTriangleLength;                                                                // Triangle Length
        float tH1 = (float) (mTriangleLength * Math.tan(Math.toRadians(mTriangleAngle)));           // Triangle Height
        float tL2 = (float) (2 * mTriangleCornerRadius / Math.tan(Math.toRadians(mTriangleAngle))); // Triangle Length
        float tH2 = 2 * mTriangleCornerRadius;                                                      // Triangle Height

        Path path = new Path();

        switch (style) {
            case STYLE_WHATSAPP: {
                float rT = width - rx - tL2 - (2 * m);                   // Rectangle Top
                float rB = width - tL2 - tL1 - (2 * rx) - (2 * m);       // Rectangle Bottom
                float rR = height - tH1 - ry - (2 * m);                  // Rectangle Right
                float rL = height - (2 * ry) - (2 * m);                  // Rectangle Left

                path.moveTo(rx + m, m);                               // Start at
                path.rLineTo(rT, 0);                                 // Top Line
                path.rQuadTo(tL2, tr, 0, tH2);                      // Top Right Corner
                path.rLineTo(-(tL1), (tH1 - tH2));                       // Triangle Slant Line
                path.rLineTo(0, rR);                                 // Right Line
                path.rQuadTo(0, ry, -rx, ry);                       // Bottom Right Corner
                path.rLineTo(-rB, 0);                                // Bottom Line
                path.rQuadTo(-rx, 0, -rx, -ry);                     // Bottom Left Corner
                path.rLineTo(0, -rL);                                // Left Line
                path.rQuadTo(0, -ry, rx, -ry);                      // Top Right corner
                path.close();
            }
            break;
            case STYLE_IPHONE: {
                float p = 5;                                            // min Padding
                float rT = width - tL1 - (2 * rx) - p - (2 * m);        // Rectangle Top
                float rB = width - tL1 - (2 * rx) + p - (2 * m);         // Rectangle Bottom
                float rR = height - (2.5f * ry) - (2 * m);              // Rectangle Right
                float rL = height - (2 * ry) - (2 * m);                 // Rectangle Left

                path.moveTo(m, m + ry);                                                    // Start at
                path.rLineTo(0, rL);                                                      // Left Line
                path.rQuadTo(0, ry, rx, ry);                                             // Bottom Left Corner
                path.rLineTo(rB, 0);                                                      // Bottom Line
                path.rQuadTo(rx / 2, 0, rx / 2 + p, -ry / 2);                     // Bottom Right Corner
                path.rQuadTo(((rx / 2 + p) + tL1) / 2, ry / 2, ((rx / 2 + p) + tL1), ry / 2);    // Bottom curve Bottom
                path.rQuadTo(-((rx / 2 + p) + tL1) / 2, 0, -((rx / 2 + p) + tL1), -ry * 1.5f); // Bottom curve Top
                path.rLineTo(0, -rR);                                                     // Right Line
                path.rQuadTo(0, -ry, -rx, -ry);                                          // Top Right Corner
                path.rLineTo(-rT, 0);                                                     // Top Line
                path.rQuadTo(-rx, 0, -rx, ry);                                           // Top Left corner
                path.close();
            }
            break;
        }
        return path;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int h = 0; //current height
        int w = 0; //current width
        int maxWidth = 0;
        int maxHeight = 0;
        mLayoutWidth = MeasureSpec.getSize(widthMeasureSpec);
        mLayoutHeight = MeasureSpec.getSize(heightMeasureSpec);

        mOrigin = new Point();
        int constant = 8;  // Tweak

        // move origin to desired location
        if (mSender) {
            mOrigin.x = (int) (mMargin + mPadding);
            mOrigin.y = (int) (mMargin + mPadding);
        } else {
            mOrigin.x = (int) (mMargin + mTriangleLength + mPadding + constant);
            mOrigin.y = (int) (mMargin + mPadding);
        }

        int x1 = mOrigin.x, y1 = mOrigin.y; // Top/Left corner

        int numOfChildren = this.getChildCount();

        // get max height/width for calculating any child with match parent as height/width
        if (getLayoutParams().height == WRAP_CONTENT || getLayoutParams().width == WRAP_CONTENT) {
            for (int i = 0; i < numOfChildren; i++) {
                int tw = 0, th = 0;
                View child = this.getChildAt(i);
                if (child.getVisibility() != GONE) {
                    this.measureChild(child, widthMeasureSpec, heightMeasureSpec);
                    LayoutParams params = (LayoutParams) child.getLayoutParams();
                    tw = mOrigin.x + child.getMeasuredWidth() + params.leftMargin + params.rightMargin;
                    th = mOrigin.y + child.getMeasuredHeight() + params.topMargin + params.bottomMargin;
                }
                maxWidth = Math.max(maxWidth, tw);
                maxHeight = Math.max(maxHeight, th);
            }
        }

        for (int i = 0; i < numOfChildren; i++) {

            int x2 = mOrigin.x, y2 = mOrigin.y; //new point for the next view

            View child = this.getChildAt(i);

            // add offset only if the view is visible/invisible
            if (child.getVisibility() != GONE) {
                this.measureChild(child, widthMeasureSpec, heightMeasureSpec);
                LayoutParams params = (LayoutParams) child.getLayoutParams();
                int vw = child.getMeasuredWidth();
                int vh = child.getMeasuredHeight();


                if (mSender) {
                    x1 = x1 + params.rightMargin;
                    y1 = y1 + params.topMargin;
                    vw = vw + params.leftMargin;
                    vh = vh + params.bottomMargin;
                } else {
                    x1 = x1 + params.leftMargin;
                    y1 = y1 + params.topMargin;
                    vw = vw + params.rightMargin;
                    vh = vh + params.bottomMargin;
                }

                // if Chat Layout has height/width as WRAP_CONTENT adjust Child with height/width as MATCH_PARENT
                if (getLayoutParams().width == WRAP_CONTENT || getLayoutParams().height == WRAP_CONTENT) {

                    int newWidth = vw, newHeight = vh;

                    if (getOrientation() == VERTICAL)
                        if (params.width == MATCH_PARENT) {
                            newWidth = maxWidth - x1;
                            if (params.height == MATCH_PARENT)
                                newHeight = vh - y1 - (int) (mMargin + mPadding);
                            LayoutParams newParams = new LinearLayout.LayoutParams(newWidth, newHeight);
                            child.setLayoutParams(newParams);
                        }

                    if (getOrientation() == HORIZONTAL)
                        if (params.height == MATCH_PARENT) {
                            if (params.width == MATCH_PARENT)
                                newWidth = vw - x1 - (int) (mMargin + mPadding);
                            newHeight = maxHeight - y1;
                            LayoutParams newParams = new LinearLayout.LayoutParams(newWidth, newHeight);
                            child.setLayoutParams(newParams);
                        }

                } else {
                    // if height/width is MATCH_PARENT adjust Child such that it leaves space for margin and padding
                    int newWidth = vw, newHeight = vh;

                    if (params.width == MATCH_PARENT) {
                        if (mSender)
                            newWidth = mLayoutWidth - x1 - (int) (mMargin + mTriangleLength + mPadding + constant);
                        else
                            newWidth = mLayoutWidth - x1 - (int) (mMargin + mPadding);
                        LayoutParams newParams = new LinearLayout.LayoutParams(newWidth, newHeight);
                        child.setLayoutParams(newParams);
                    }

                    if (params.height == MATCH_PARENT) {
                        newHeight = mLayoutHeight - y1 - (int) (mMargin + mPadding);
                        LayoutParams newParams = new LinearLayout.LayoutParams(newWidth, newHeight);
                        child.setLayoutParams(newParams);
                    }
                }

                if (getOrientation() == VERTICAL) {

                    //reposition the point on the next line
                    y2 = y1 + vh; //add view height to the current height
                    x2 = mOrigin.x;

                    //latest height/width: current point + height/width of the view
                    //however if the previous height/width is larger use that one
                    h = Math.max(h, y2);
                    w = Math.max(w, x1 + vw);
                } else {

                    //reposition the point on the next column
                    y2 = mOrigin.y;
                    x2 = x1 + vw;

                    //latest height/width: current point + height/width of the view
                    //however if the previous height/width is larger use that one
                    h = Math.max(h, y1 + vh);
                    w = Math.max(w, x2);
                }
            }

            // Store Child origin for onLayout()
            Point point = new Point();
            point.x = x1;
            point.y = y1;
            mOriginList.add(point);

            // adjust origin at the end of current Child
            x1 = x2;
            y1 = y2;
        }

        if (mSender) {
            w = (int) (w + 2 * mMargin + 2 * mPadding) + constant;
            h = (int) (h + 2 * mMargin + 2 * mPadding) - (int) (2.5 * constant);
        } else {
            w = (int) (w + 2 * mMargin + 2 * mPadding - mTriangleLength);
            h = (int) (h + 2 * mMargin + 2 * mPadding) - (int) (2.5 * constant);
        }

        setMeasuredDimension(resolveSize(w, widthMeasureSpec), resolveSize(h, heightMeasureSpec));
    }


    @Override
    protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {
        //Call layout() on children
        int numOfChildren = this.getChildCount();
        for (int i = 0; i < numOfChildren; i++) {
            View child = this.getChildAt(i);
            Point point = mOriginList.get(i);
            child.layout(point.x,
                    point.y,
                    point.x + child.getMeasuredWidth(),
                    point.y + child.getMeasuredHeight());
        }

        // Clear the List for requestLayout
        mOriginList.clear();
    }

    public int getLayoutForegroundColor() {
        return mForegroundColor;
    }

    public void setLayoutForegroundColor(int mForegroundColor) {
        this.mForegroundColor = mForegroundColor;
        invalidate();
    }

    public int getLayoutBackgroundColor() {
        return mBackgroundColor;
    }

    public void setLayoutBackgroundColor(int mBackgroundColor) {
        this.mBackgroundColor = mBackgroundColor;
        invalidate();
    }

    public float getCornerRadius() {
        return mCornerRadius;
    }

    public void setCornerRadius(float mCornerRadius) {
        this.mCornerRadius = mCornerRadius;
        invalidate();
        requestLayout();
    }

    public float getTriangleCornerRadius() {
        return mTriangleCornerRadius;
    }

    public void setTriangleCornerRadius(float mTriangleCornerRadius) {
        this.mTriangleCornerRadius = mTriangleCornerRadius;
        invalidate();
        requestLayout();
    }

    public float getTriangleLength() {
        return mTriangleLength;
    }

    public void setTriangleLength(float mTriangleLength) {
        this.mTriangleLength = mTriangleLength;
        invalidate();
        requestLayout();
    }

    public float getTriangleAngle() {
        return mTriangleAngle;
    }

    public void setTriangleAngle(float mTriangleAngle) {
        this.mTriangleAngle = mTriangleAngle;
        invalidate();
        requestLayout();
    }

    public boolean isSender() {
        return mSender;
    }

    public void setSender(boolean mSender) {
        this.mSender = mSender;
        invalidate();
        requestLayout();
    }

    public float getMargin() {
        return mMargin;
    }

    public void setMargin(float mMargin) {
        this.mMargin = mMargin;
        invalidate();
        requestLayout();
    }

    public float getPadding() {
        return mPadding;
    }

    public void setPadding(float mPadding) {
        this.mPadding = mPadding;
        invalidate();
        requestLayout();
    }

    public int getStyle() {
        return mStyle;
    }

    public void setStyle(int mStyle) {
        this.mStyle = mStyle;
        invalidate();
        requestLayout();
    }
}