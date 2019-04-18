package com.example.xyzreader.ui.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;

import com.example.xyzreader.R;
import com.example.xyzreader.data.model.Article;
import com.example.xyzreader.ui.ArticlesViewModel;
import com.example.xyzreader.ui.HomeActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;


/**
 * A simple {@link Fragment} subclass, representing a single Article detail screen, letting you swipe between articles
 */
public class ArticlesPagerFragment extends Fragment {

    private ArticlesViewModel mViewModel;
    private ArticlesPagerAdapter mPagerAdapter;
    private ViewPager mPager;

    public ArticlesPagerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_articles_pager, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = HomeActivity.obtainViewModel(getActivity());
        setupPagerAdapter(view);
        selectCurrentItem();
    }

    private void selectCurrentItem() {
        mViewModel.getCurrentSelectedArticlePosition().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer selectedPosition) {
                mPager.setCurrentItem(selectedPosition, false);
            }
        });
    }

    private void setupPagerAdapter(View view) {
        // Enable FragmentManager logging
        FragmentManager.enableDebugLogging(true);
        mPagerAdapter = new ArticlesPagerAdapter(getFragmentManager());
        mPager = view.findViewById(R.id.pager);
        mPager.setAdapter(mPagerAdapter);

        mViewModel.getArticlesListLiveData().observe(getViewLifecycleOwner(), new Observer<List<Article>>() {
            @Override
            public void onChanged(List<Article> articles) {
                if (articles != null) {
                    mPagerAdapter.submitList(articles);
                }
            }
        });
    }
}
