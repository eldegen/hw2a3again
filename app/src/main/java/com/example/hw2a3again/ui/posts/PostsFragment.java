package com.example.hw2a3again.ui.posts;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hw2a3again.App;
import com.example.hw2a3again.R;
import com.example.hw2a3again.data.models.Post;
import com.example.hw2a3again.databinding.FragmentPostsBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostsFragment extends Fragment {

    private FragmentPostsBinding binding;
    private PostsAdapter adapter;
    private NavController controller;

    public PostsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPostsBinding.inflate(inflater, container, false);
        controller = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new PostsAdapter();
        binding.recycler.setAdapter(adapter);

        App.api.getPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter.setPosts(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to load posts! (" + t + ")", Toast.LENGTH_SHORT).show();
                Log.e("f_global", "onFailure: " + t);
            }
        });

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.navigate(R.id.action_postsFragment_to_formFragment);
            }
        });
    }
}