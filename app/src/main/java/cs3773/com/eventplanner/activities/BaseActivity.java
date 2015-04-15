package cs3773.com.eventplanner.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

import cs3773.com.eventplanner.R;
import cs3773.com.eventplanner.model.Role;
import cs3773.com.eventplanner.model.Session;

/**
 * A base activity that handles common functionality in the app. This includes the
 * navigation drawer, login and authentication, Action Bar tweaks, amongst others.
 */
public abstract class BaseActivity extends ActionBarActivity {

    // Navigation drawer:
    private DrawerLayout mDrawerLayout;

    private ViewGroup mDrawerItemsListContainer;
    private Handler mHandler;

    // symbols for navdrawer items (indices must correspond to array below). This is
    // not a list of items that are necessarily *present* in the Nav Drawer; rather,
    // it's a list of all possible items.
    protected static final int NAVDRAWER_ITEM_INVALID = -1;
    protected static final int NAVDRAWER_ITEM_CALENDAR = 0;
    protected static final int NAVDRAWER_ITEM_EVENT = 1;
    protected static final int NAVDRAWER_ITEM_CHAT = 2;
    protected static final int NAVDRAWER_ITEM_ACCOUNT_INFO = 3;
    protected static final int NAVDRAWER_ITEM_ACCOUNT_CREATE = 4;

    // titles for navdrawer items (indices must correspond to the above)
    private static final int[] NAVDRAWER_TITLE_RES_ID = new int[]{
            R.string.navdrawer_item_calendar,
            R.string.navdrawer_item_event,
            R.string.navdrawer_item_chat,
            R.string.navdrawer_item_account_info,
            R.string.navdrawer_create_account,
    };

    // icons for navdrawer items (indices must correspond to above array)
    private static final int[] NAVDRAWER_ICON_RES_ID = new int[]{
            R.drawable.ic_drawer_calendar,  // My Schedule
            R.drawable.ic_drawer_event,  // Explore
            R.drawable.ic_drawer_chat, // Map
            R.drawable.ic_drawer_account_info, // Social
            R.drawable.ic_drawer,
    };

    // delay to launch nav drawer item, to allow close animation to play
    private static final int NAVDRAWER_LAUNCH_DELAY = 250;

    // Primary toolbar and drawer toggle
    private Toolbar mActionBarToolbar;

    // list of navdrawer items that were actually added to the navdrawer, in order
    private ArrayList<Integer> mNavDrawerItems = new ArrayList<Integer>();

    // views that correspond to each navdrawer item, null if not yet created
    private View[] mNavDrawerItemViews = null;

    // A Runnable that we should execute when the navigation drawer finishes its closing animation
    private Runnable mDeferredOnDrawerClosedRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHandler = new Handler();

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * Returns the navigation drawer item that corresponds to this Activity. Subclasses
     * of BaseActivity override this to indicate what nav drawer item corresponds to them
     * Return NAVDRAWER_ITEM_INVALID to mean that this Activity should not have a Nav Drawer.
     */
    protected int getSelfNavDrawerItem() {
        return NAVDRAWER_ITEM_INVALID;
    }

    /**
     * Sets up the navigation drawer as appropriate. Note that the nav drawer will be
     * different depending on whether the attendee indicated that they are attending the
     * event on-site vs. attending remotely.
     */
    private void setupNavDrawer() {
        // What nav drawer item should be selected?
        int selfItem = getSelfNavDrawerItem();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout); // FIXME: this is always null
        if (mDrawerLayout == null) {
            System.out.println("******************** mdrawerlayout is null");
            return;
        }
        mDrawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.background_material_dark));
        ScrollView navDrawer = (ScrollView) mDrawerLayout.findViewById(R.id.navdrawer);
        if (selfItem == NAVDRAWER_ITEM_INVALID) {
            System.out.println("************************do not show navbar");
            // do not show a nav drawer
            if (navDrawer != null) {
                ((ViewGroup) navDrawer.getParent()).removeView(navDrawer);
            }
            mDrawerLayout = null;
            return;
        }
        System.out.println("*************************show navbar");

        if (mActionBarToolbar != null) {
            System.out.println("*********************** action toolbar is not null");
            mActionBarToolbar.setNavigationIcon(R.drawable.ic_drawer);
            mActionBarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDrawerLayout.openDrawer(Gravity.START);
                }
            });
        } else {
            System.out.println("***********************action toolbar is null");
        }

        mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerClosed(View drawerView) {
                // run deferred action, if we have one
                if (mDeferredOnDrawerClosedRunnable != null) {
                    mDeferredOnDrawerClosedRunnable.run();
                    mDeferredOnDrawerClosedRunnable = null;
                }
                onNavDrawerStateChanged(false, false);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                onNavDrawerStateChanged(true, false);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                onNavDrawerStateChanged(isNavDrawerOpen(), newState != DrawerLayout.STATE_IDLE);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
            }
        });

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, Gravity.START);

        // populate the nav drawer with the correct items
        populateNavDrawer();

    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        getActionBarToolbar();
    }

    // Subclasses can override this for custom behavior
    protected void onNavDrawerStateChanged(boolean isOpen, boolean isAnimating) {
    }

    protected boolean isNavDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(Gravity.START);
    }

    protected void closeNavDrawer() {
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(Gravity.START);
        }
    }

    /**
     * Populates the navigation drawer with the appropriate items.
     */
    private void populateNavDrawer() {
        mNavDrawerItems.clear();

        // always shown
        mNavDrawerItems.add(NAVDRAWER_ITEM_CALENDAR);
        mNavDrawerItems.add(NAVDRAWER_ITEM_EVENT);
        mNavDrawerItems.add(NAVDRAWER_ITEM_CHAT);
        mNavDrawerItems.add(NAVDRAWER_ITEM_ACCOUNT_INFO);

        if (Session.getAccount().getRole() == Role.admin)
            mNavDrawerItems.add(NAVDRAWER_ITEM_ACCOUNT_CREATE);

        createNavDrawerItems();
    }

    @Override
    public void onBackPressed() {
        if (isNavDrawerOpen()) {
            closeNavDrawer();
        } else {
            super.onBackPressed();
        }
    }

    private void createNavDrawerItems() {
        mDrawerItemsListContainer = (ViewGroup) findViewById(R.id.navdrawer_items_list);
        if (mDrawerItemsListContainer == null) {
            return;
        }

        mNavDrawerItemViews = new View[mNavDrawerItems.size()];
        mDrawerItemsListContainer.removeAllViews();
        int i = 0;
        for (int itemId : mNavDrawerItems) {
            mNavDrawerItemViews[i] = makeNavDrawerItem(itemId, mDrawerItemsListContainer);
            mDrawerItemsListContainer.addView(mNavDrawerItemViews[i]);
            ++i;
        }
    }

    /**
     * Sets up the given navdrawer item's appearance to the selected state. Note: this could
     * also be accomplished (perhaps more cleanly) with state-based layouts.
     */
    private void setSelectedNavDrawerItem(int itemId) {
        if (mNavDrawerItemViews != null) {
            for (int i = 0; i < mNavDrawerItemViews.length; i++) {
                if (i < mNavDrawerItems.size()) {
                    int thisItemId = mNavDrawerItems.get(i);
                    formatNavDrawerItem(mNavDrawerItemViews[i], thisItemId, itemId == thisItemId);
                }
            }
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setupNavDrawer();
    }

    private void goToNavDrawerItem(int item) {
        Intent intent;
        switch (item) {
            case NAVDRAWER_ITEM_CALENDAR:
                intent = new Intent(this, CalendarActivity.class);
                startActivity(intent);
                finish();
                break;
            case NAVDRAWER_ITEM_EVENT:
                intent = new Intent(this, EventActivity.class);
                startActivity(intent);
                finish();
                break;
            case NAVDRAWER_ITEM_CHAT:
                intent = new Intent(this, ChatActivity.class);
                startActivity(intent);
                finish();
                break;
            case NAVDRAWER_ITEM_ACCOUNT_INFO:
                intent = new Intent(this, AccountInfoActivity.class);
                startActivity(intent);
                finish();
                break;
            case NAVDRAWER_ITEM_ACCOUNT_CREATE:
                intent = new Intent(this, CreateAccountActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void onNavDrawerItemClicked(final int itemId) {
        if (itemId == getSelfNavDrawerItem()) {
            mDrawerLayout.closeDrawer(Gravity.START);
            return;
        }

//        if (isSpecialItem(itemId)) {
//            goToNavDrawerItem(itemId);
//        } else {
        // launch the target Activity after a short delay, to allow the close animation to play
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                goToNavDrawerItem(itemId);
            }
        }, NAVDRAWER_LAUNCH_DELAY);

        // change the active item on the list so the user can see the item changed
        setSelectedNavDrawerItem(itemId);
        // fade out the main content
//        View mainContent = findViewById(R.id.main_content);
//        if (mainContent != null) {
//            mainContent.animate().alpha(0).setDuration(100);
//        }
//        }

        mDrawerLayout.closeDrawer(Gravity.START);
    }

    public void showDialog(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Okay", null)
                .show();
    }

    public void errorDialog(String message) {
        showDialog("Error", message);
    }

    /**
     * Converts an intent into a {@link Bundle} suitable for use as fragment arguments.
     */
    public static Bundle intentToFragmentArguments(Intent intent) {
        Bundle arguments = new Bundle();
        if (intent == null) {
            return arguments;
        }

        final Uri data = intent.getData();
        if (data != null) {
            arguments.putParcelable("_uri", data);
        }

        final Bundle extras = intent.getExtras();
        if (extras != null) {
            arguments.putAll(intent.getExtras());
        }

        return arguments;
    }

    /**
     * Converts a fragment arguments bundle into an intent.
     */
    public static Intent fragmentArgumentsToIntent(Bundle arguments) {
        Intent intent = new Intent();
        if (arguments == null) {
            return intent;
        }

        final Uri data = arguments.getParcelable("_uri");
        if (data != null) {
            intent.setData(data);
        }

        intent.putExtras(arguments);
        intent.removeExtra("_uri");
        return intent;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private View makeNavDrawerItem(final int itemId, ViewGroup container) {
        boolean selected = getSelfNavDrawerItem() == itemId;
        int layoutToInflate = R.layout.navdrawer_item;
        View view = getLayoutInflater().inflate(layoutToInflate, container, false);

        ImageView iconView = (ImageView) view.findViewById(R.id.icon);
        TextView titleView = (TextView) view.findViewById(R.id.title);
        int iconId = itemId >= 0 && itemId < NAVDRAWER_ICON_RES_ID.length ? NAVDRAWER_ICON_RES_ID[itemId] : 0;
        int titleId = itemId >= 0 && itemId < NAVDRAWER_TITLE_RES_ID.length ?
                NAVDRAWER_TITLE_RES_ID[itemId] : 0;

        // set icon and text
        iconView.setVisibility(iconId > 0 ? View.VISIBLE : View.GONE);
        if (iconId > 0) {
            iconView.setImageResource(iconId);
        }
        titleView.setText(getString(titleId));

        formatNavDrawerItem(view, itemId, selected);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNavDrawerItemClicked(itemId);
            }
        });

        return view;
    }

    private void formatNavDrawerItem(View view, int itemId, boolean selected) {
        TextView titleView = (TextView) view.findViewById(R.id.title);

        if (selected) {
            view.setBackgroundResource(R.drawable.selected_navdrawer_item_background);
        }

        // configure its appearance according to whether or not it's selected
        titleView.setTextColor(selected ?
                getResources().getColor(R.color.navdrawer_text_color_selected) :
                getResources().getColor(R.color.navdrawer_text_color));
    }

    protected Toolbar getActionBarToolbar() {
        if (mActionBarToolbar == null) {
            mActionBarToolbar = (Toolbar) findViewById(R.id.action_bar);
            if (mActionBarToolbar != null) {
                setSupportActionBar(mActionBarToolbar);
            }
        }
        return mActionBarToolbar;
    }
}
