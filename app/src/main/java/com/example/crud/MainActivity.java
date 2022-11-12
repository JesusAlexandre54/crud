package com.example.crud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //criar arquivo database openhelper
    private SQLiteDatabase bancoDados;
    //criar srquivo tipo listview
    public ListView listViewDados;
    //criar botãb
    public Button botao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewDados = (ListView) findViewById(R.id.listviewDados);
        botao = findViewById(R.id.buttonCadastrar);
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirTelaCadastro();
            }
        });

        //criar metodo para criação do banco de dados
        criarBancoDados();

        //criar metodo inserir dados
        inserirDadosTemp();

        //criar metodo para exibição do banco de dados
        listarDados();
    }

    private void abrirTelaCadastro() {
        Intent intent = new Intent(this, CadastroActivity.class);
        startActivity(intent);

    }


    //chamar o metodo criado
    private void criarBancoDados() {
        try {
            bancoDados = openOrCreateDatabase("crudapp", MODE_PRIVATE, null);
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS coisa(" + "id INTEGER PRIMARY KEY AUTOINCREMENT " + ",nome VARCHAR )");
            bancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //listar os dados
    public void listarDados() {

        try {
            bancoDados = openOrCreateDatabase("crudapp", MODE_PRIVATE, null);
            //criando o cursor
            Cursor meuCursor = bancoDados.rawQuery("SELECT id, nome FROM coisa ", null);
            //criando o array list
            ArrayList<String> linhas = new ArrayList<String>();
            //criando o adapter
            ArrayAdapter meuAdapter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    linhas
            );
            listViewDados.setAdapter(meuAdapter);
            meuCursor.moveToFirst();
            while (meuCursor != null) {
                linhas.add(meuCursor.getString(1));
                meuCursor.moveToNext();

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void inserirDadosTemp() {
        try {
            bancoDados = openOrCreateDatabase("crudapp", MODE_PRIVATE, null);
            String sql = "INSERT INTO coisa (nome) VALUES(?)";
            SQLiteStatement stmt = bancoDados.compileStatement(sql);


            bancoDados.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


}