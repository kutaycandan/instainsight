package com.kutaycandan.instainsight.ui.activity;

import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.kutaycandan.instainsight.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmptyActivity extends BaseActivity {

    @BindView(R.id.chart1)
    LineChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);
        ButterKnife.bind(this);

        mChart.setDragEnabled(false);
        mChart.setScaleEnabled(false);
        mChart.setTouchEnabled(false);

        ArrayList<Entry> yValues = new ArrayList<>();

        yValues.add(new Entry(0,9));
        yValues.add(new Entry(1,19));
        yValues.add(new Entry(2,21));
        yValues.add(new Entry(3,5));
        yValues.add(new Entry(4,15));
        yValues.add(new Entry(5,17));
        yValues.add(new Entry(6,9));
        LineDataSet set1 = new LineDataSet(yValues,"Label");
        set1.setColor(getResources().getColor(R.color.text_black));
        set1.setLineWidth(2f);
        set1.setValueTextColor(getResources().getColor(R.color.chart_red));
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

        Description description = new Description();
        description.setText("");
        mChart.setDescription(description);
        mChart.getAxisRight().setDrawLabels(false);
        mChart.getXAxis().setDrawLabels(false);
        mChart.getLegend().setEnabled(false);
        LineData data= new LineData(dataSets);
        mChart.setData(data);
    }

    public class ValuesFormatter implements IValueFormatter {

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            return ""+(int)value;
        }
    }
}
