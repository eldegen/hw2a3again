package com.example.hw2a3again.ui.form;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hw2a3again.App;
import com.example.hw2a3again.R;
import com.example.hw2a3again.data.models.Post;
import com.example.hw2a3again.databinding.FragmentFormBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormFragment extends Fragment {

    private FragmentFormBinding binding;
    private final int USER_ID = 13;
    private final int GROUP_ID = 39;

    public FormFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFormBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bufferTitle = binding.edTitle.getText().toString();
                String bufferContent = binding.edContent.getText().toString();

                Post post = new Post(bufferTitle, bufferContent, GROUP_ID, USER_ID);
                App.api.createPost(post).enqueue(new Callback<Post>() {
                    @Override
                    public void onResponse(Call<Post> call, Response<Post> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), "Successfully posted!", Toast.LENGTH_SHORT).show();
                            requireActivity().onBackPressed();
                        }
                    }

                    @Override
                    public void onFailure(Call<Post> call, Throwable t) {
                        Toast.makeText(getContext(), "Failed to post this message! (" + t + ")", Toast.LENGTH_SHORT).show();
                        Log.e("f_global", "onFailure: " + t);
                    }
                });
            }
        });
    }
}