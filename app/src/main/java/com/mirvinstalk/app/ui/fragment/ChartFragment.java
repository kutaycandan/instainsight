package com.mirvinstalk.app.ui.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.mirvinstalk.app.R;
import com.mirvinstalk.app.ui.activity.EmptyActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ChartFragment extends Fragment {
    ArrayList<Integer> likeList;
    Unbinder unbinder;
    @BindView(R.id.chart1)
    LineChart mChart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart, container, false);
        unbinder = ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            likeList = bundle.getIntegerArrayList("likeList");
            chartInit();
        }
        return view;
    }

    public void chartInit(){
        mChart.setDragEnabled(false);
        mChart.setScaleEnabled(false);
        mChart.setTouchEnabled(false);

        ArrayList<Entry> yValues = new ArrayList<>();

        yValues.add(new Entry(0,likeList.get(0)));
        yValues.add(new Entry(1,likeList.get(1)));
        yValues.add(new Entry(2,likeList.get(2)));
        yValues.add(new Entry(3,likeList.get(3)));
        yValues.add(new Entry(4,likeList.get(4)));
        yValues.add(new Entry(5,likeList.get(5)));
        yValues.add(new Entry(6,likeList.get(6)));
        LineDataSet set1 = new LineDataSet(yValues,"Label");
        set1.setColor(getResources().getColor(R.color.text_black));
        set1.setLineWidth(2f);
        set1.setValueTextColor(getResources().getColor(R.color.chart_red));
        set1.setValueTypeface(Typeface.createFromAsset(getResources().getAssets(), "hurme_semi_bold.otf"));
        set1.setCircleColor(getResources().getColor(R.color.text_black));
        set1.setCircleColorHole(getResources().getColor(R.color.text_black));
        set1.setValueFormatter(new ValuesFormatter());
        set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        set1.setValueTextSize(12f);
        set1.setCubicIntensity(0.15f);
        set1.setDrawFilled(true);
        set1.setFillDrawable(getResources().getDrawable(R.drawable.gradient_chart));
        ArrayList<ILineDataSet> dataSets= new ArrayList<>();
        dataSets.add(set1);

        mChart.getAxisRight().setEnabled(false);
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setAxisMinimum(0);
        leftAxis.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "hurme_bold.otf"));
        leftAxis.setTextColor(getResources().getColor(R.color.text_black));
        leftAxis.setTextSize(14f);
        Description description = new Description();
        description.setText("");
        mChart.setDescription(description);
        mChart.getAxisRight().setDrawLabels(false);
        mChart.getXAxis().setDrawLabels(false);
        mChart.getLegend().setEnabled(false);
        LineData data= new LineData(dataSets);
        mChart.setData(data);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public class ValuesFormatter implements IValueFormatter {

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            return ""+(int)value;
        }
    }
}
