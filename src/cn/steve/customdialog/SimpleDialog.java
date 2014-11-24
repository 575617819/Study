package cn.steve.customdialog;



import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import cn.steve.customdialog.dialog.Effectstype;
import cn.steve.customdialog.dialog.NiftyDialogBuilder;
import cn.steve.study.R;
public class SimpleDialog extends Activity {
	private Button mButton = null;
	//�Զ����Ч������
	private Effectstype mEffect;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_simpletest);
		//����Ч������
		mEffect=Effectstype.Fadein;
		mButton = (Button) findViewById(R.id.dialog_simple_btn);
		//���ü�����
		mButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//�Ի��������
				NiftyDialogBuilder dialogBuilder=NiftyDialogBuilder.getInstance(SimpleDialog.this);
			    	//����չʾ�ĶԻ��������
				    dialogBuilder
	                .withTitle("���ǶԻ���ı���")                                 
	                .withTitleColor("#FFFFFF")                                  
	                .withDividerColor("#11000000")                            
	                .withMessage("This is a modal Dialog.")                    
	                .withMessageColor("#FFFFFF")                                
	                .withIcon(getResources().getDrawable(R.drawable.customer_icon))
	                .isCancelableOnTouchOutside(true)                           
	                .withDuration(700)                                          
	                .withEffect(mEffect)                                        
	                .withButton1Text("��һ����ť��ʾ������")                                      
	                .withButton2Text("�ڶ�����ť��ʾ������")                                  
	                .setCustomView(R.layout.dialog_view,SimpleDialog.this) 
	                //���õ����һ����ť��ʱ�����Ӧ����
	                .setButton1Click(new View.OnClickListener() {
	                    @Override
	                    public void onClick(View v) {
	                        Toast.makeText(v.getContext(), "��һ������", Toast.LENGTH_SHORT).show();
	                    }
	                })
	                //���õ���ڶ�����ť��ʱ�����Ӧ����
	                .setButton2Click(new View.OnClickListener() {
	                    @Override
	                    public void onClick(View v) {
	                        Toast.makeText(v.getContext(), "�ڶ��������", Toast.LENGTH_SHORT).show();
	                    }
	                })
	                //���ú���չʾ
	                .show();
			}
		});

	}
}
