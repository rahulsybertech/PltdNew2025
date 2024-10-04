package com.syber.ssspltd.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.syber.ssspltd.R;
import com.syber.ssspltd.adapter.MoreItemsAdapter;
import com.syber.ssspltd.response.MoreItems;

import java.util.ArrayList;
import java.util.List;


public class MoreFragment extends Fragment {

    List<MoreItems> moreItemsList;
    MoreItems moreItems;
    MoreItemsAdapter moreItemsAdapter;

    String[] item_name = {"Profile","Branches","Bank Details","About Us","Feedback","FAQ","Logout","Contact Us"};
    String[] item_id = {"1", "2", "3", "4","5","6","7","8"};
    Integer item_Img[] = {R.drawable.profile,
            R.drawable.office, R.drawable.bank, R.drawable.information, R.drawable.rating, R.drawable.faq,
            R.drawable.logout, R.drawable.ic_support};

    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_more, container, false);



        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        moreItemsList = new ArrayList<>();

        recyclerView = view.findViewById(R.id.more_recycler);

        for (int i = 0; i < item_name.length; i++) {
            moreItems = new MoreItems(item_id[i], item_name[i], item_Img[i]);
            moreItemsList.add(moreItems);
        }

        moreItemsAdapter = new MoreItemsAdapter(getContext(), moreItemsList);
        recyclerView.setAdapter(moreItemsAdapter);
    }
}