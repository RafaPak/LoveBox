package cotuca.unicamp.projetopratica;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ConnectionActivity extends AppCompatActivity {

    public int port = 3000;
    public String host = "177.220.18.110";

    EditText mensagem;
    TextView sttConexao;
    Button enviar, sair, conectar;
    String mensagemEnviar;

    Socket socket = null;
    PrintWriter pw = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        mensagem = (EditText)findViewById(R.id.edTxtMensagem);
        sttConexao = (TextView)findViewById(R.id.sttConexao);
        enviar = (Button)findViewById(R.id.btnEnviar);
        sair = (Button)findViewById(R.id.btnSair);
        conectar = (Button)findViewById(R.id.btnConectar);

        /* if (getIntent() != null) {
            port = getIntent().getIntExtra("PORTA", 0);
            host = getIntent().getStringExtra("ENDERECO");
        } */

        conectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    new ConexaoSocket().execute();

                }
                catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Erro na conexão"  , Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                    sttConexao.setText("Desconectado");
                }
            }
        });

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (!mensagem.getText().toString().isEmpty())
                        mensagemEnviar = mensagem.getText().toString();
                    else
                        Toast.makeText(getApplicationContext(), "Escreva algo válido", Toast.LENGTH_SHORT).show();

                    if (socket.isConnected() && !socket.isClosed())
                    {
                        new Writer().execute(mensagemEnviar);
                    }
                    else
                        Toast.makeText(getApplicationContext(), "Não está conectado", Toast.LENGTH_LONG).show();
                }
                catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Problemas no envio", Toast.LENGTH_SHORT).show();
                }
            }
        });

        sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (!socket.isClosed()) {
                        socket.close();
                        pw.close();
                    }
                }
                catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Erro na saída", Toast.LENGTH_SHORT).show();
                }

                finish();
            }
        });
    }

    class ConexaoSocket extends AsyncTask<Void, Void, Socket>
    {
        @Override
        protected Socket doInBackground(Void... voids) {
            try {
                socket = new Socket(host, port);
            } catch (IOException e) {
                return null;
            }

            return socket;
        }

        @Override
        protected void onPostExecute(Socket socket) {
            if (socket.isConnected())
                sttConexao.setText("Conectado");

            try {
                pw = new PrintWriter(socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class Writer extends AsyncTask<String, String, String>
    {
        @Override
        protected String doInBackground(String... strings) {
            pw.write(strings[0]);
            pw.flush();
            return strings[0];
        }
    }
}
