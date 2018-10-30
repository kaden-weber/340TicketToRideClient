package weber.kaden.myapplication.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import weber.kaden.common.model.ChatMessage;
import weber.kaden.myapplication.R;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private List<ChatMessage> data;
    private LayoutInflater inflater;

    ChatAdapter(Context context, List<ChatMessage> data) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycle_view_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String message = data.get(position).toString();
        holder.messageWindow.setText(message);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView messageWindow;
        ViewHolder(View itemView) {
            super(itemView);
            messageWindow = itemView.findViewById(R.id.chat_window);
        }

        @Override
        public void onClick(View v) {}
    }
}
