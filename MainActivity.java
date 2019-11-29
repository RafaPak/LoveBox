package cotuca.unicamp.projetopratica;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    EditText porta, endereco;
    Button conectar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        porta = (EditText)findViewById(R.id.edTxtPorta);
        endereco = (EditText)findViewById(R.id.edTxtEndereco);
        conectar = (Button)findViewById(R.id.btnConectar);

        conectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
            if (!endereco.getText().toString().isEmpty() && !porta.getText().toString().isEmpty()) {
                Intent i = new Intent(MainActivity.this, ConnectionActivity.class);
                i.putExtra("PORTA", Integer.parseInt(porta.getText().toString()));
                i.putExtra("ENDERECO", endereco.getText().toString());
                startActivity(i);
            }
            }
        });
    }
}
