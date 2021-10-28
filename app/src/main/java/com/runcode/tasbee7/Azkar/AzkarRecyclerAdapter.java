package com.runcode.tasbee7.Azkar;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.runcode.tasbee7.R;
import com.runcode.tasbee7.Tasbeeh.TasbeehViewModel;

import java.util.List;

public class AzkarRecyclerAdapter extends RecyclerView.Adapter<AzkarRecyclerAdapter.AzkarViewHolder> {

    private List<Zikr> data;
    private final Context mContext;
    private static final String TAG = "AzkarRecyclerAdapter";

    public AzkarRecyclerAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public AzkarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.zkir_item, parent, false);
        return new AzkarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AzkarViewHolder holder, int position) {
        Zikr zikr = data.get(position);
        holder.zikrText.setText(zikr.getZikrText());
        holder.zikrCount.setText(String.valueOf(zikr.getNumOfCount()));

        View.OnClickListener clickListener = view -> {
            zikr.setNumOfCount(zikr.getNumOfCount() - 1);
            holder.zikrCount.setText(String.valueOf(zikr.getNumOfCount()));
            if (zikr.getNumOfCount() == 0) {
                if (position != RecyclerView.NO_POSITION) {
                    onItemDismiss(position);
                    Log.d(TAG, "onBindViewHolder: " + position);
                }
            }
        };
        holder.mCardView.setOnClickListener(clickListener);
        holder.containerIncrease.setOnClickListener(clickListener);
        holder.share.setOnClickListener(view -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, zikr.getZikrText());
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            mContext.startActivity(shareIntent);
        });
    }

    public void onItemDismiss(int position) {
        if (position != -1 && position < data.size()) {
            data.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, getItemCount());
        }
        if (data.isEmpty()) {
            Toast.makeText(mContext, "تمت قراءة الاذكار", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        if (data != null) {
            return data.size();
        } else {
            return 0;
        }
    }

    public void setData(List<Zikr> data) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(
                new ZikrDiffUtil(this.data, data));
        this.data = data;
        diffResult.dispatchUpdatesTo(this);
        notifyDataSetChanged();
    }


    public static class ZikrDiffUtil extends DiffUtil.Callback {
        private final List<Zikr> oldDataList;
        private final List<Zikr> newDataList;

        public ZikrDiffUtil(List<Zikr> oldData, List<Zikr> newData) {
            this.oldDataList = oldData;
            this.newDataList = newData;
        }

        @Override
        public int getOldListSize() {
            if (oldDataList != null) {
                return oldDataList.size();
            } else {
                return 0;
            }
        }

        @Override
        public int getNewListSize() {
            if (newDataList != null) {
                return newDataList.size();
            } else {
                return 0;
            }
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldDataList.get(oldItemPosition).equals(newDataList.get(newItemPosition));
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            Zikr oldData = oldDataList.get(oldItemPosition);
            Zikr newData = newDataList.get(newItemPosition);
            return oldData.getZikrId() == newData.getZikrId();
        }
    }

    public static class AzkarViewHolder extends RecyclerView.ViewHolder {
        TextView zikrText, zikrCount;
        CardView mCardView;
        LinearLayout containerIncrease ;
        Button share;

        public AzkarViewHolder(@NonNull View itemView) {
            super(itemView);
            zikrCount = itemView.findViewById(R.id.item_zikr_count);
            zikrText = itemView.findViewById(R.id.text_zikr_item);
            mCardView = itemView.findViewById(R.id.card_item_zikr_container);
            containerIncrease = itemView.findViewById(R.id.container_increment);
            share = itemView.findViewById(R.id.share);
            changeButtonsColor(itemView);
        }

        private void changeButtonsColor(@NonNull View itemView) {
            int color = getColorPrimary(itemView.getContext());
            int colorDarker = TasbeehViewModel.getDarkerColor(color,0.8f);
            GradientDrawable shareGradientDrawable= (GradientDrawable) share.getBackground().mutate();
            shareGradientDrawable.setColor(color);
            GradientDrawable gradientDrawableCount = (GradientDrawable) containerIncrease.getBackground().mutate();
            GradientDrawable gradientDrawableCount2 = (GradientDrawable) zikrCount.getBackground().mutate();
            gradientDrawableCount.setColor(colorDarker);
            gradientDrawableCount2.setColor(colorDarker);

        }

        public Integer getColorPrimary(Context context) {
            final String sColor_primary = "color_primary";
            final String SHARED_PREF = "shared_pref";
            SharedPreferences sp = context.getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
            return sp.getInt(sColor_primary,
                    context.getResources().getColor(R.color.colorPrimary));
        }
    }


}