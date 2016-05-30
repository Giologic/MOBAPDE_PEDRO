package com.giologic.pedrosystem10;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import com.giologic.pedrosystem10.R;
import com.giologic.pedrosystem10.model.Post;
import com.giologic.pedrosystem10.model.Question;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by MSI LEOPARD on 1/27/2016.
 */
public class AnswersAdapter extends RecyclerView.Adapter<AnswersAdapter.AnswerHolder>
{


    private ArrayList<Question> items = new ArrayList<Question>();
    public AnswersAdapter(ArrayList<Question> items) {this.items = items;};
    public AnswersAdapter(){};
    //Creates the class for song holder to contain the list of songs
    public class AnswerHolder extends RecyclerView.ViewHolder {
        TextView tvQuestion;
        TextView tvAnswers;

        public AnswerHolder (View itemView) {
            super(itemView);
            tvQuestion = (TextView) itemView.findViewById(R.id.tv_question);
            tvAnswers = (TextView) itemView.findViewById(R.id.tv_answers);
        }
    }

    public void add(Question object) {
        items.add(object);
        notifyDataSetChanged();
    }

    public void add(int index, Question object) {
        items.add(index, object);
        notifyDataSetChanged();
    }

    public void addAll(Collection<Question> collection) {
        if (collection != null) {
            items.addAll(collection);
            notifyDataSetChanged();
        }
    }

    @Override
    public AnswerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // TODO : inflate XML
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_answers, parent, false);
        return new AnswerHolder(v);
    }

    @Override
    public void onBindViewHolder(AnswerHolder holder, int position) {
        //TODO : change data of text view to song of given position
        holder.tvQuestion.setText(items.get(position).getContents());
        holder.tvAnswers.setText(items.get(position).getAnswer());

    }

    public void addAll(String... items) {
        addAll(items);
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public void remove(String object) {
        items.remove(object);
        notifyDataSetChanged();
    }

    public void removeAll() {
        items.removeAll(items);
    }

    public Question getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).hashCode();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
