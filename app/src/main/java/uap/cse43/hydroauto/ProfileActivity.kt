package uap.cse43.hydroauto

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.View
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_main.*


class ProfileActivity : AppCompatActivity() {


     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         setContentView(R.layout.activity_main)



         btn_scan.setOnClickListener { it: View? ->
             val scanner = IntentIntegrator(this)
             scanner.setBeepEnabled(false)
             scanner.initiateScan()

         }

         showdata.setOnClickListener {
             val intent = Intent(this, ShowDataActivity::class.java)
             startActivity(intent)
         }
         showprofile.setOnClickListener {
             val intent = Intent(this, ShowProfileActivity::class.java)
             startActivity(intent)
         }
         btn_notify.setOnClickListener {
             val intent = Intent(this, NotificationActivity::class.java)
             startActivity(intent)
         }
     }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== Activity.RESULT_OK){
            val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            if(result != null) {
                if(result.getContents() == null) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }


}