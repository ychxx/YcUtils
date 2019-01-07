package com.yc.ycutilslibrary.common;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.FloatRange;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringRes;
import android.support.v4.text.util.LinkifyCompat;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.classic.adapter.interfaces.ImageLoad;
import com.yc.ycutilslibrary.common.adapter.YcAdapter;

/**
 *
 */
@SuppressWarnings({"unused", "WeakerAccess", "UnusedReturnValue"})
public class BaseViewHolderHelper {
    /**
     * Views indexed with their IDs
     */
    private final SparseArray<View> mViews;
    private final Context mContext;

    private View mConvertView;
    private int mPosition;
    private int mLayoutId;
    private ImageLoad mImageLoad;
    /**
     * Package private field to retain the associated user object and detect a change
     */
    Object mAssociatedObject;

    protected BaseViewHolderHelper(Context context, ViewGroup parent, int layoutId, int position) {
        this.mContext = context;
        this.mPosition = position;
        this.mLayoutId = layoutId;
        this.mViews = new SparseArray<>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        mConvertView.setTag(this);
        if (null != YcAdapter.singleton && null != YcAdapter.singleton.getImageLoad()) {
            mImageLoad = YcAdapter.singleton.getImageLoad();
        }
    }

    /**
     * This method is the only entry point to get a BaseViewHolderHelper.
     *
     * @param context     The current context.
     * @param convertView The convertView arg passed to the getView() method.
     * @param parent      The parent arg passed to the getView() method.
     * @return A BaseViewHolderHelper instance.
     */
    public static BaseViewHolderHelper get(Context context, View convertView, ViewGroup parent, int layoutId) {
        return get(context, convertView, parent, layoutId, -1);
    }

    static BaseViewHolderHelper get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new BaseViewHolderHelper(context, parent, layoutId, position);
        }

        // Retrieve the existing helper and update its position
        BaseViewHolderHelper existingHelper = (BaseViewHolderHelper) convertView.getTag();

        if (existingHelper.mLayoutId != layoutId) {
            return new BaseViewHolderHelper(context, parent, layoutId, position);
        }

        existingHelper.mPosition = position;
        return existingHelper;
    }

    /**
     * Retrieve the mConvertView
     */
    public View getView() {
        return mConvertView;
    }

    /**
     * This method allows you to retrieve a view and perform custom
     * operations on it, not covered by the BaseViewHolderHelper.
     * If you think it's a common use case, please consider creating
     * a new issue at https://github.com/JoanZapata/base-adapter-helper/issues.
     *
     * @param viewId The id of the view you want to retrieve.
     */
    public <T extends View> T getView(@IdRes int viewId) {
        return retrieveView(viewId);
    }

    /**
     * Retrieve the overall mPosition of the mData in the list.
     *
     * @throws IllegalArgumentException If the mPosition hasn't been set at the construction of the
     *                                  this helper.
     */
    public int getPosition() {
        if (mPosition == -1) {
            throw new IllegalStateException("Use BaseViewHolderHelper constructor " +
                    "with mPosition if you need to retrieve the mPosition.");
        }
        return mPosition;
    }

    /**
     * Retrieves the last converted object on this view.
     */
    public Object getAssociatedObject() {
        return mAssociatedObject;
    }

    /**
     * Should be called during convert
     */
    public void setAssociatedObject(Object associatedObject) {
        this.mAssociatedObject = associatedObject;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public BaseViewHolderHelper setBackground(@IdRes int viewId, @NonNull Drawable drawable) {
        retrieveView(viewId).setBackground(drawable);
        return this;
    }

    /**
     * Will set background color of a view.
     *
     * @param viewId The view id.
     * @param color  A color, not a resource id.
     * @return The BaseViewHolderHelper for chaining.
     */
    public BaseViewHolderHelper setBackgroundColor(@IdRes int viewId, @ColorInt int color) {
        retrieveView(viewId).setBackgroundColor(color);
        return this;
    }

    /**
     * Will set background of a view.
     *
     * @param viewId        The view id.
     * @param backgroundRes A resource to use as a background.
     * @return The BaseViewHolderHelper for chaining.
     */
    public BaseViewHolderHelper setBackgroundRes(@IdRes int viewId, @DrawableRes int backgroundRes) {
        retrieveView(viewId).setBackgroundResource(backgroundRes);
        return this;
    }

    /**
     * Will set the text of a TextView.
     *
     * @param viewId The view id.
     * @param value  The text to put in the text view.
     * @return The BaseViewHolderHelper for chaining.
     */
    public BaseViewHolderHelper setText(@IdRes int viewId, CharSequence value) {
        ((TextView) retrieveView(viewId)).setText(value);
        return this;
    }

    public BaseViewHolderHelper setTextRes(@IdRes int viewId, @StringRes int resId) {
        ((TextView) retrieveView(viewId)).setText(resId);
        return this;
    }

    /**
     * Will set text color of a TextView.
     *
     * @param viewId    The view id.
     * @param textColor The text color (not a resource id).
     * @return The BaseViewHolderHelper for chaining.
     */
    public BaseViewHolderHelper setTextColor(@IdRes int viewId, @ColorInt int textColor) {
        ((TextView) retrieveView(viewId)).setTextColor(textColor);
        return this;
    }

    /**
     * Will set text color of a TextView.
     *
     * @param viewId       The view id.
     * @param textColorRes The text color resource id.
     * @return The BaseViewHolderHelper for chaining.
     */
    public BaseViewHolderHelper setTextColorRes(@IdRes int viewId, @ColorRes int textColorRes) {
        //noinspection deprecation
        ((TextView) retrieveView(viewId)).setTextColor(mContext.getResources().getColor(textColorRes));
        return this;
    }

    /**
     * Will set text color of a TextView.
     *
     * @param viewId       The view id.
     * @param textColorRes The text color resource id.
     * @param theme        theme The theme used to style the color attributes, may be
     *                     {@code null}.
     * @return The BaseViewHolderHelper for chaining.
     */
    @TargetApi(Build.VERSION_CODES.M)
    public BaseViewHolderHelper setTextColorRes(
            @IdRes int viewId, @ColorRes int textColorRes, @Nullable Resources.Theme theme) {
        ((TextView) retrieveView(viewId)).setTextColor(mContext.getResources().getColor(textColorRes, theme));
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public BaseViewHolderHelper setImageIcon(@IdRes int viewId, @NonNull Icon icon) {
        ((ImageView) retrieveView(viewId)).setImageIcon(icon);
        return this;
    }

    /**
     * Will set the image of an ImageView from a resource id.
     *
     * @param viewId     The view id.
     * @param imageResId The image resource id.
     * @return The BaseViewHolderHelper for chaining.
     */
    public BaseViewHolderHelper setImageResource(@IdRes int viewId, @DrawableRes int imageResId) {
        ((ImageView) retrieveView(viewId)).setImageResource(imageResId);
        return this;
    }

    /**
     * Will set the image of an ImageView from a drawable.
     *
     * @param viewId   The view id.
     * @param drawable The image drawable.
     * @return The BaseViewHolderHelper for chaining.
     */
    public BaseViewHolderHelper setImageDrawable(@IdRes int viewId, @NonNull Drawable drawable) {
        ((ImageView) retrieveView(viewId)).setImageDrawable(drawable);
        return this;
    }

    /**
     * Will download an image from a URL and put it in an ImageView.
     *
     * @param viewId   The view id.
     * @param imageUrl The image URL.
     * @return The BaseViewHolderHelper for chaining.
     */
    public BaseViewHolderHelper setImageUrl(@IdRes int viewId, @NonNull String imageUrl) {
        if (null != this.mImageLoad) {
            ImageView view = retrieveView(viewId);
            this.mImageLoad.load(mContext, view, imageUrl);
        }
        return this;
    }

    /**
     * Custom network load images
     */
    public BaseViewHolderHelper setImageLoad(@NonNull ImageLoad imageLoad) {
        this.mImageLoad = imageLoad;
        return this;
    }

    /**
     * Add an action to set the image of an image view. Can be called multiple times.
     */
    public BaseViewHolderHelper setImageBitmap(@IdRes int viewId, @NonNull Bitmap bitmap) {
        ((ImageView) retrieveView(viewId)).setImageBitmap(bitmap);
        return this;
    }

    /**
     * Add an action to set the alpha of a view. Can be called multiple times.
     * Alpha between 0-1.
     */
    public BaseViewHolderHelper setAlpha(@IdRes int viewId, @FloatRange(from = 0.0, to = 1.0) float value) {
        retrieveView(viewId).setAlpha(value);
        return this;
    }

    /**
     * Set a view visibility to VISIBLE (true) or GONE (false).
     *
     * @param viewId  The view id.
     * @param visible True for VISIBLE, false for GONE.
     * @return The BaseViewHolderHelper for chaining.
     */
    public BaseViewHolderHelper setVisible(@IdRes int viewId, boolean visible) {
        retrieveView(viewId).setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    public BaseViewHolderHelper setVisible(@IdRes int viewId, int visibility) {
        retrieveView(viewId).setVisibility(visibility);
        return this;
    }

    public BaseViewHolderHelper setEnabled(@IdRes int viewId, boolean enabled) {
        retrieveView(viewId).setEnabled(enabled);
        return this;
    }

    public BaseViewHolderHelper setFocusable(@IdRes int viewId, boolean focusable) {
        retrieveView(viewId).setFocusable(focusable);
        return this;
    }

    public BaseViewHolderHelper setFocusableInTouchMode(@IdRes int viewId, boolean focusableInTouchMode) {
        retrieveView(viewId).setFocusableInTouchMode(focusableInTouchMode);
        return this;
    }

    /**
     * Add All links into a TextView.
     *
     * @param viewId TextView id.
     * @return The BaseViewHolderHelper for chaining.
     */
    public BaseViewHolderHelper addAllLinks(@IdRes int viewId) {
        addLinks(viewId, Linkify.ALL);
        return this;
    }

    /**
     * Add links into a TextView.
     *
     * @param viewId TextView id.
     * @return The BaseViewHolderHelper for chaining.
     * @see android.text.util.Linkify#addLinks(TextView text, int mask)
     */
    public BaseViewHolderHelper addLinks(@IdRes int viewId, @LinkifyCompat.LinkifyMask int mask) {
        TextView view = retrieveView(viewId);
        Linkify.addLinks(view, mask);
        return this;
    }

    /**
     * Apply the typeface to the given viewId, and enable sub pixel rendering.
     */
    public BaseViewHolderHelper setTypeface(@IdRes int viewId, @NonNull Typeface typeface) {
        TextView view = retrieveView(viewId);
        view.setTypeface(typeface);
        view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        return this;
    }

    /**
     * Apply the typeface to all the given viewIds, and enable sub pixel rendering.
     */
    public BaseViewHolderHelper setTypeface(@NonNull Typeface typeface, @IdRes int... viewIds) {
        for (int viewId : viewIds) {
            setTypeface(viewId, typeface);
        }
        return this;
    }

    public BaseViewHolderHelper setTypeface(@IdRes int viewId, Typeface typeface, int style) {
        TextView view = retrieveView(viewId);
        view.setTypeface(typeface, style);
        view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        return this;
    }

    /**
     * Sets the progress of a ProgressBar.
     *
     * @param viewId   The view id.
     * @param progress The progress.
     * @return The BaseViewHolderHelper for chaining.
     */
    public BaseViewHolderHelper setProgress(@IdRes int viewId, int progress) {
        ((ProgressBar) retrieveView(viewId)).setProgress(progress);
        return this;
    }

    /**
     * Sets the progress and max of a ProgressBar.
     *
     * @param viewId   The view id.
     * @param progress The progress.
     * @param max      The max value of a ProgressBar.
     * @return The BaseViewHolderHelper for chaining.
     */
    public BaseViewHolderHelper setProgress(@IdRes int viewId, int progress, int max) {
        ProgressBar view = retrieveView(viewId);
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }

    /**
     * Sets the range of a ProgressBar to 0...max.
     *
     * @param viewId The view id.
     * @param max    The max value of a ProgressBar.
     * @return The BaseViewHolderHelper for chaining.
     */
    public BaseViewHolderHelper setMax(@IdRes int viewId, int max) {
        ((ProgressBar) retrieveView(viewId)).setMax(max);
        return this;
    }

    /**
     * Sets the rating (the number of stars filled) of a RatingBar.
     *
     * @param viewId The view id.
     * @param rating The rating.
     * @return The BaseViewHolderHelper for chaining.
     */
    public BaseViewHolderHelper setRating(@IdRes int viewId, float rating) {
        ((RatingBar) retrieveView(viewId)).setRating(rating);
        return this;
    }

    /**
     * Sets the rating (the number of stars filled) and max of a RatingBar.
     *
     * @param viewId The view id.
     * @param rating The rating.
     * @param max    The range of the RatingBar to 0...max.
     * @return The BaseViewHolderHelper for chaining.
     */
    public BaseViewHolderHelper setRating(@IdRes int viewId, float rating, int max) {
        RatingBar view = retrieveView(viewId);
        view.setMax(max);
        view.setRating(rating);
        return this;
    }

    /**
     * Sets the tag of the view.
     *
     * @param viewId The view id.
     * @param tag    The tag;
     * @return The BaseViewHolderHelper for chaining.
     */
    public BaseViewHolderHelper setTag(@IdRes int viewId, @NonNull Object tag) {
        retrieveView(viewId).setTag(tag);
        return this;
    }

    /**
     * Sets the tag of the view.
     *
     * @param viewId The view id.
     * @param key    The key of tag;
     * @param tag    The tag;
     * @return The BaseViewHolderHelper for chaining.
     */
    public BaseViewHolderHelper setTag(@IdRes int viewId, int key, @NonNull Object tag) {
        retrieveView(viewId).setTag(key, tag);
        return this;
    }

    /**
     * Sets the checked status of a checkable.
     *
     * @param viewId  The view id.
     * @param checked The checked status;
     * @return The BaseViewHolderHelper for chaining.
     */
    public BaseViewHolderHelper setChecked(@IdRes int viewId, boolean checked) {
        View view = retrieveView(viewId);
        if (view instanceof CompoundButton) {
            ((CompoundButton) view).setChecked(checked);
        } else if (view instanceof CheckedTextView) {
            ((CheckedTextView) view).setChecked(checked);
        }
        return this;
    }

    /**
     * Sets the adapter of a adapter view.
     *
     * @param viewId  The view id.
     * @param adapter The adapter;
     * @return The BaseViewHolderHelper for chaining.
     */
    @SuppressWarnings("unchecked")
    public BaseViewHolderHelper setAdapter(@IdRes int viewId, @NonNull Adapter adapter) {
        ((AdapterView) retrieveView(viewId)).setAdapter(adapter);
        return this;
    }

    public BaseViewHolderHelper setAdapter(@IdRes int viewId, @NonNull RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
        ((RecyclerView) retrieveView(viewId)).setAdapter(adapter);
        return this;
    }

    /**
     * Sets the on click listener of the view.
     *
     * @param viewId   The view id.
     * @param listener The on click listener;
     * @return The BaseViewHolderHelper for chaining.
     */
    public BaseViewHolderHelper setOnClickListener(@IdRes int viewId, @NonNull View.OnClickListener listener) {
        retrieveView(viewId).setOnClickListener(listener);
        return this;
    }

    /**
     * Sets the on touch listener of the view.
     *
     * @param viewId   The view id.
     * @param listener The on touch listener;
     * @return The BaseViewHolderHelper for chaining.
     */
    public BaseViewHolderHelper setOnTouchListener(@IdRes int viewId, @NonNull View.OnTouchListener listener) {
        retrieveView(viewId).setOnTouchListener(listener);
        return this;
    }

    /**
     * Sets the on long click listener of the view.
     *
     * @param viewId   The view id.
     * @param listener The on long click listener;
     * @return The BaseViewHolderHelper for chaining.
     */
    public BaseViewHolderHelper setOnLongClickListener(@IdRes int viewId, @NonNull View.OnLongClickListener listener) {
        retrieveView(viewId).setOnLongClickListener(listener);
        return this;
    }

    /**
     * Sets the ListView or GridView's item click listener of the view
     *
     * @param viewId   The view id.
     * @param listener The item on click listener;
     * @return The BaseViewHolderHelper for chaining.
     */
    public BaseViewHolderHelper setOnItemClickListener(@IdRes int viewId,
                                                       @NonNull AdapterView.OnItemClickListener listener) {
        ((AdapterView) retrieveView(viewId)).setOnItemClickListener(listener);
        return this;
    }

    /**
     * Sets the ListView or GridView's item long click listener of the view
     *
     * @param viewId   The view id.
     * @param listener The item long click listener;
     * @return The BaseViewHolderHelper for chaining.
     */
    public BaseViewHolderHelper setOnItemLongClickListener(@IdRes int viewId,
                                                           @NonNull AdapterView.OnItemLongClickListener listener) {
        ((AdapterView) retrieveView(viewId)).setOnItemLongClickListener(listener);
        return this;
    }

    /**
     * Sets the ListView or GridView's item selected click listener of the view
     *
     * @param viewId   The view id.
     * @param listener The item selected click listener;
     * @return The BaseViewHolderHelper for chaining.
     */
    public BaseViewHolderHelper setOnItemSelectedClickListener(@IdRes int viewId,
                                                               @NonNull AdapterView.OnItemSelectedListener listener) {
        ((AdapterView) retrieveView(viewId)).setOnItemSelectedListener(listener);
        return this;
    }

    @SuppressWarnings("unchecked")
    private <T extends View> T retrieveView(@IdRes int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

}
