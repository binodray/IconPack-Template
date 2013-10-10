package adapters;

import java.util.List;

import your.icons.name.here.R;
import android.animation.AnimatorInflater;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.internal.nineoldandroids.animation.Animator;
import com.actionbarsherlock.internal.nineoldandroids.animation.Animator.AnimatorListener;
import com.actionbarsherlock.internal.nineoldandroids.animation.AnimatorSet;


public class RequestIconsAdapter extends BaseAdapter {
	private Context context;
	private List<AdapterItem> gridItem;

	public RequestIconsAdapter(Context context, List<AdapterItem> gridItem) {
		this.gridItem = gridItem;
		this.context = context;
		
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		AdapterItem entry = gridItem.get(position);

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.request_item, null);

			holder = new ViewHolder();
			holder.txtCode = (TextView) convertView.findViewById(R.id.txtCode);
			holder.txtName = (TextView) convertView.findViewById(R.id.txtName);
			holder.imgIcon = (ImageView) convertView.findViewById(R.id.imgIcon);
			holder.chkSelected = (CheckBox) convertView.findViewById(R.id.chkSelected);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.txtCode.setText(entry.getCode());
		holder.txtName.setText(entry.getName());
		holder.imgIcon.setImageDrawable(entry.getImage());
		holder.chkSelected.setChecked(entry.isSelected());

		return convertView;
	}

	@Override
	public int getCount() {
		return gridItem.size();
	}

	@Override
	public Object getItem(int position) {
		return gridItem.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public static class ViewHolder {
		public TextView txtCode;
		public TextView txtName;
		public ImageView imgIcon;
		public CheckBox chkSelected;
		
		public FrameLayout Card;
		public FrameLayout btnContact;
		public ImageView imgContact;
		public ImageView imgSelected;
		public View bgSelected;
		public TextView txtSender;
		public TextView txtPreview;
		public TextView txtDate;
		public View viewNew;
	}

	public static class AdapterItem {
		String Code;
		String Name;
		Drawable Image;
		Boolean Selected;

		/** CONSTRUCTORS **/
		public AdapterItem() {
			this.Code = null;
			this.Name = null;
			this.Image = null;
			this.Selected = null;
		}

		public AdapterItem(String Code, String Name, Drawable Image,
				Boolean Selected) {
			this.Code = Code;
			this.Name = Name;
			this.Image = Image;
			this.Selected = Selected;
		}
		
		public void animateView(int position, ListView list)
		{
			View v = list.getChildAt(position - list.getFirstVisiblePosition());

			ViewHolder holder = new ViewHolder();
			holder.Card = (FrameLayout) v.findViewById(R.id.Card);
			holder.btnContact = (FrameLayout) v.findViewById(R.id.btnContact);
			holder.imgContact = (ImageView) v.findViewById(R.id.imgContact);
			holder.imgSelected = (ImageView) v.findViewById(R.id.imgSelected);
			holder.bgSelected = v.findViewById(R.id.bgSelected);

			if (mSelectedItemsIds.get(position))
				animateAppDeselected(holder);
			else
				animateAppSelected(holder);

			mListener.onPhotoClick(position);
		}
		private void animateAppSelected(final ViewHolder holderFinal)
		{
			// Declare AnimatorSets
			final AnimatorSet animOut = (AnimatorSet) 
					AnimatorInflater.loadAnimator(context, R.anim.card_flip_right_out);
			final AnimatorSet animIn = (AnimatorSet) 
					AnimatorInflater.loadAnimator(context, R.anim.card_flip_left_in);
			animOut.setTarget(holderFinal.btnContact);
			animIn.setTarget(holderFinal.btnContact);
			animOut.addListener(new AnimatorListener()
			{
				@Override
				public void onAnimationCancel(Animator animation)
				{
					// Nothing
				}

				@Override
				public void onAnimationEnd(Animator animation)
				{
					holderFinal.btnContact.setClickable(true);
					selectCard(true, holderFinal.Card);
					holderFinal.bgSelected.setVisibility(View.VISIBLE);
					holderFinal.imgSelected.setVisibility(View.VISIBLE);
					animIn.start();
				}

				@Override
				public void onAnimationRepeat(Animator animation)
				{
					// Nothing
				}

				@Override
				public void onAnimationStart(Animator animation)
				{
					holderFinal.btnContact.setClickable(false);
					selectCard(false, holderFinal.Card);
					holderFinal.bgSelected.setVisibility(View.GONE);
					holderFinal.imgSelected.setVisibility(View.GONE);
				}
			});
			animOut.start();
		}

		private void animateAppDeselected(final ViewHolder holderFinal)
		{
			// Declare AnimatorSets
			final AnimatorSet animOut = (AnimatorSet) 
					AnimatorInflater.loadAnimator(context, R.anim.card_flip_left_out);
			final AnimatorSet animIn = (AnimatorSet) 
					AnimatorInflater.loadAnimator(context, R.anim.card_flip_right_in);
			animOut.setTarget(holderFinal.btnContact);
			animIn.setTarget(holderFinal.btnContact);
			animOut.addListener(new AnimatorListener()
			{
				@Override
				public void onAnimationCancel(Animator animation)
				{
					// Nothing
				}

				@Override
				public void onAnimationEnd(Animator animation)
				{
					holderFinal.btnContact.setClickable(true);
					selectCard(false, holderFinal.Card);
					holderFinal.bgSelected.setVisibility(View.GONE);
					holderFinal.imgSelected.setVisibility(View.GONE);
					animIn.start();
				}

				@Override
				public void onAnimationRepeat(Animator animation)
				{
					// Nothing
				}

				@Override
				public void onAnimationStart(Animator animation)
				{
					holderFinal.btnContact.setClickable(false);
					selectCard(true, holderFinal.Card);
					holderFinal.bgSelected.setVisibility(View.VISIBLE);
					holderFinal.imgSelected.setVisibility(View.VISIBLE);
				}
			});
			animOut.start();
		}

		/** SETTERS **/
		public void setCode(String Code) {
			this.Code = Code;
		}

		public void setName(String Name) {
			this.Name = Name;
		}

		public void setImage(Drawable Image) {
			this.Image = Image;
		}

		public void setSelected(boolean Selected) {
			this.Selected = Selected;
		}

		/** GETTERS **/
		public String getCode() {
			return Code;
		}

		public String getName() {
			return Name;
		}

		public Drawable getImage() {
			return Image;
		}

		public boolean isSelected() {
			return Selected;
		}
	}
	
}
