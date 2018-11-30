package gaskuadmin.funcode.funcode.com.gogasadmin;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import gaskuadmin.funcode.funcode.com.gogasadmin.fragment.Pager;
import gaskuadmin.funcode.funcode.com.gogasadmin.fragment.PesananFragment;
import gaskuadmin.funcode.funcode.com.gogasadmin.fragment.PesananSelesaiFragment;

public class PesananActivity extends AppCompatActivity {
    Toolbar toolbar;
    private FloatingActionButton pesan;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesanan);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        pesan = (FloatingActionButton) findViewById(R.id.addpesanan);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        viewPager.setOffscreenPageLimit(1);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PesananActivity.this, AddPesananActivity.class);
                startActivity(i);
            }
        });
    }


    private void setupViewPager(ViewPager viewPager) {
        Pager adapter = new Pager(getSupportFragmentManager());
        adapter.addFragment(new PesananFragment(), "Berlangsung");
        adapter.addFragment(new PesananSelesaiFragment(), "Selesai");
        viewPager.setAdapter(adapter);
    }
}
