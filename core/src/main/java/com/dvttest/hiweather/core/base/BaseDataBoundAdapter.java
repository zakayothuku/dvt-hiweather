package com.dvttest.hiweather.core.base;

import android.view.ViewGroup;

import androidx.annotation.CallSuper;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.databinding.OnRebindCallback;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.dvttest.hiweather.core.recyclerview.DataBoundViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A reference implementation for an adapter that wants to use body binding "the right way". It
 * works with {@link DataBoundViewHolder}.
 * <p>
 * It listens for layout invalidations and notifies RecyclerView about them before views refresh
 * themselves. It also avoids invalidating the full item when body in the bound item dispatches
 * proper notify events.
 * <p>
 * This class uses layout id as the item type.
 * <p>
 * It can be used for both single type lists and multiple type lists.
 *
 * @param <T> The type of the ViewDataBinding class. Can be ommitted in multiple-binding-type use
 *            case.
 */
public abstract class BaseDataBoundAdapter<T extends ViewDataBinding> extends RecyclerView.Adapter<DataBoundViewHolder<T>> {

    static final Object DB_PAYLOAD = new Object();

    @Nullable
    RecyclerView mRecyclerView;

    /**
     * This is used to block items from updating themselves. RecyclerView wants to know when an
     * item is invalidated and it prefers to refresh it via onRebind. It also helps with performance
     * since body binding will not update views that are not changed.
     */
    private final OnRebindCallback mOnRebindCallback = new OnRebindCallback() {
        @Override
        public boolean onPreBind(ViewDataBinding binding) {
            if (mRecyclerView == null || mRecyclerView.isComputingLayout()) {
                return true;
            }
            int childAdapterPosition = mRecyclerView.getChildAdapterPosition(binding.getRoot());
            if (childAdapterPosition == RecyclerView.NO_POSITION) {
                return true;
            }
            notifyItemChanged(childAdapterPosition, DB_PAYLOAD);
            return false;
        }
    };

    @Override
    @CallSuper
    public DataBoundViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        DataBoundViewHolder<T> vh = DataBoundViewHolder.Companion.create(parent, viewType);
        vh.getBinding().addOnRebindCallback(mOnRebindCallback);
        return vh;
    }

    @Override
    public final void onBindViewHolder(DataBoundViewHolder<T> holder, int position, List<Object> payloads) {
        // when a VH is rebound to the same item, we don't have to call the setters
        if (payloads.isEmpty() || hasNonDataBindingInvalidate(payloads)) {
            bindItem(holder, position, payloads);
        }
        holder.getBinding().executePendingBindings();
    }

    /**
     * Override this method to handle binding your items into views
     *
     * @param holder   The ViewHolder that has the binding instance
     * @param position The position of the item in the adapter
     * @param payloads The payloads that were passed into the onBind method
     */
    protected abstract void bindItem(DataBoundViewHolder<T> holder, int position, List<Object> payloads);

    private boolean hasNonDataBindingInvalidate(List<Object> payloads) {
        for (Object payload : payloads) {
            if (payload != DB_PAYLOAD) {
                return true;
            }
        }
        return false;
    }

    @Override
    public final void onBindViewHolder(@NotNull DataBoundViewHolder<T> holder, int position) {
        throw new IllegalArgumentException("just overridden to make final.");
    }

    @Override
    public void onAttachedToRecyclerView(@NotNull RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(@NotNull RecyclerView recyclerView) {
        mRecyclerView = null;
    }

    @Override
    public final int getItemViewType(int position) {
        return getItemLayoutId(position);
    }

    @LayoutRes
    public abstract int getItemLayoutId(int position);
}
