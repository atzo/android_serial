package com.example.masterkey;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Toast;

public class ButtonAdapter extends BaseAdapter
{
    private Context mContext;
    private int btn_id;
    private int total_btns = 10;
    private UsbService usbService;

    public ButtonAdapter(Context context, UsbService usbService) {
        SharedPreferences sharedPref =  context.getSharedPreferences(context.getString(R.string.preference_key),  Context.MODE_PRIVATE);
        total_btns = sharedPref.getInt(context.getString(R.string.button_count), total_btns);
        this.mContext = context;
        this.usbService = usbService;
    }

    @Override
    public int getCount() {
        return total_btns;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Button btn;


        if (convertView == null) {
            btn = new Button(mContext);
        } else {
            btn = (Button) convertView;
        }

        btn.setText(String.format("Door \n%03d", position + 1));

        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String data = "Door #" + (position + 1);
                if (usbService != null) { // if UsbService was correctly binded, Send data
                    usbService.write((data+"\n").getBytes());
                    Toast.makeText(v.getContext(), data + " was sent!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(v.getContext(), data + " not sent\nSerial port is not connected!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return btn;
    }
}
