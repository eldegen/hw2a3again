package com.example.hw2a3again.ui.posts;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hw2a3again.App;
import com.example.hw2a3again.R;
import com.example.hw2a3again.data.models.Post;
import com.example.hw2a3again.databinding.ItemPostBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private List<Post> posts = new ArrayList<>();

    public void setPosts(List<Post> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPostBinding binding = ItemPostBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.onBind(posts.get(position));
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setTitle("Delete post")
                        .setMessage("Are you sure you want to delete this post?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                App.api.deletePost(posts.get(position).getId()).enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        Toast.makeText(v.getContext(), "Successfully deleted!", Toast.LENGTH_SHORT).show();
                                        posts.remove(position);
                                        notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        Toast.makeText(v.getContext(), "Something goes wrong... (" + t + ")", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

                return false;
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("getId", posts.get(position).getId());
                bundle.putString("getTitle", posts.get(position).getTitle());
                bundle.putString("getContent", posts.get(position).getContent());
                bundle.putInt("getUser", posts.get(position).getUserId());
                bundle.putInt("getGroup", posts.get(position).getGroupId());

                Navigation.findNavController((Activity) v.getContext(), R.id.nav_host_fragment).navigate(R.id.formFragment, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private ItemPostBinding binding;
        public ViewHolder(@NonNull ItemPostBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void onBind(Post post) {
            HashMap<Integer, String> names = new HashMap<>();
            names.put(0, "Server");
            names.put(13, "Eldeg");
            String bufferName = names.getOrDefault(post.getUserId(), "Someone else");

            binding.tvUserId.setText(bufferName);
            binding.tvTitle.setText(post.getTitle());
            binding.tvContent.setText(post.getContent());
        }
    }
}
