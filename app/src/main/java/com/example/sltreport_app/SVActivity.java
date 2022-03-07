package com.example.sltreport_app;

        import androidx.appcompat.app.AppCompatActivity;

        import android.os.Bundle;
        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;
        import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

        import android.os.Bundle;
        import android.widget.Toast;

        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.ValueEventListener;

        import java.util.ArrayList;
public class SVActivity extends AppCompatActivity {
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    SVAdapter adapter;
    DAOEmployee dao;
    boolean isLoading=false;
    String key =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sv);
        swipeRefreshLayout = findViewById(R.id.swipsv);
        recyclerView = findViewById(R.id.sv);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        adapter= new SVAdapter(this);
        recyclerView.setAdapter(adapter);
        dao = new DAOEmployee();
        String email = getIntent().getStringExtra("Key4");


        loadData();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy)
            {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int totalItem = linearLayoutManager.getItemCount();
                int lastVisible = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                if(totalItem< lastVisible+3)
                {
                    if(!isLoading)
                    {
                        isLoading=true;
                        loadData();
                    }
                }
            }
        });
    }

    private void loadData()
    {

        swipeRefreshLayout.setRefreshing(true);
        dao.get(key).addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                ArrayList<Report> emps = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren())
                {
                    Report emp = data.getValue(Report.class);
                    emp.setKey(data.getKey());
                    emps.add(emp);
                    key = data.getKey();
                }
                adapter.setItems(emps);
                adapter.notifyDataSetChanged();
                isLoading =false;
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}