package cn.steve.customview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import cn.steve.signature.Utils;
import cn.steve.study.R;

public class CustomerPaintActivity extends Activity {
	private Button mClearButton;
	private Button mSaveButton;
	private CustomerPaintView mPaintView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���Զ���Ŀؼ�����Ϊ��������
		// setContentView(new MyPaintView(this));
		// ʹ�ò����ļ�
		setContentView(R.layout.activity_customer_paint);
		mPaintView = (CustomerPaintView) findViewById(R.id.view_paint);
		mSaveButton = (Button) findViewById(R.id.btn_save);

		mClearButton = (Button) findViewById(R.id.btn_clear);
		mClearButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				mPaintView.clear();
			}
		});

		mSaveButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Utils.storeInSD(mPaintView.save_img());

			}
		});

	}
}