package com.project.cerberus.mumbleclient.app;

import android.content.Context;
import android.widget.ArrayAdapter;

public class DrawerAdapter extends ArrayAdapter<DrawerAdapter.DrawerRow> {
    public static final int HEADER_CONNECTED_SERVER = 0;
    public static final int HEADER_GENERAL = 9;
    public static final int HEADER_SERVERS = 5;
    private static final int HEADER_TYPE = 0;
    public static final int ITEM_ACCESS_TOKENS = 3;
    public static final int ITEM_FAVOURITES = 6;
    public static final int ITEM_INFO = 2;
    public static final int ITEM_PINNED_CHANNELS = 4;
    public static final int ITEM_PUBLIC = 8;
    public static final int ITEM_SERVER = 1;
    public static final int ITEM_SETTINGS = 10;
    private static final int ITEM_TYPE = 1;
    private DrawerDataProvider mProvider;

    public interface DrawerDataProvider {
        String getConnectedServerName();

        boolean isConnected();
    }

    public static class DrawerRow {
        int id;
        String title;

        private DrawerRow(int id, String title) {
            this.id = id;
            this.title = title;
        }
    }

    public static class DrawerHeader extends DrawerRow {
        public DrawerHeader(int id, String title) {
            super(id, title);
        }
    }

    public static class DrawerItem extends DrawerRow {
        int icon;

        public DrawerItem(int id, String title, int icon) {
            super(id, title);
            this.icon = icon;
        }
    }

    public DrawerAdapter(Context context, DrawerDataProvider provider) {
        super(context, 0);
        this.mProvider = provider;
//        add(new DrawerHeader(0, context.getString(R.string.drawer_not_connected)));
//        add(new DrawerItem(1, context.getString(R.string.drawer_server), R.drawable.ic_action_channels));
//        add(new DrawerItem(2, context.getString(R.string.information), R.drawable.ic_action_info_dark));
//        add(new DrawerItem(3, context.getString(R.string.drawer_tokens), R.drawable.ic_action_save));
//        add(new DrawerItem(4, context.getString(R.string.drawer_pinned), R.drawable.ic_action_comment));
//        add(new DrawerHeader(5, context.getString(R.string.drawer_header_servers)));
//        add(new DrawerItem(6, context.getString(R.string.drawer_favorites), R.drawable.ic_action_favourite_on));
//        add(new DrawerItem(8, context.getString(R.string.drawer_public), R.drawable.ic_action_search));
//        add(new DrawerHeader(9, context.getString(R.string.general)));
//        add(new DrawerItem(10, context.getString(R.string.action_settings), R.drawable.ic_action_settings));
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.view.View getView(int r13, android.view.View r14, android.view.ViewGroup r15) {
        /*
        r12 = this;
        r11 = 1;
        r10 = 0;
        r6 = r14;
        r7 = r12.getItemViewType(r13);
        if (r6 != 0) goto L_0x001a;
    L_0x0009:
        if (r7 != 0) goto L_0x0039;
    L_0x000b:
        r8 = r12.getContext();
        r8 = android.view.LayoutInflater.from(r8);
        r9 = 2130903082; // 0x7f03002a float:1.7412972E38 double:1.0528060074E-314;
        r6 = r8.inflate(r9, r15, r10);
    L_0x001a:
        if (r7 != 0) goto L_0x005d;
    L_0x001c:
        r1 = r12.getItem(r13);
        r1 = (com.project.cerberus.mumbleclient.app.DrawerAdapter.DrawerHeader) r1;
        r8 = 2131427459; // 0x7f0b0083 float:1.8476535E38 double:1.053065084E-314;
        r5 = r6.findViewById(r8);
        r5 = (android.widget.TextView) r5;
        r8 = r12.getItemId(r13);
        r8 = (int) r8;
        switch(r8) {
            case 0: goto L_0x004b;
            default: goto L_0x0033;
        };
    L_0x0033:
        r8 = r1.title;
        r5.setText(r8);
    L_0x0038:
        return r6;
    L_0x0039:
        if (r7 != r11) goto L_0x001a;
    L_0x003b:
        r8 = r12.getContext();
        r8 = android.view.LayoutInflater.from(r8);
        r9 = 2130903083; // 0x7f03002b float:1.7412974E38 double:1.052806008E-314;
        r6 = r8.inflate(r9, r15, r10);
        goto L_0x001a;
    L_0x004b:
        r8 = r12.mProvider;
        r8 = r8.isConnected();
        if (r8 == 0) goto L_0x0033;
    L_0x0053:
        r8 = r12.mProvider;
        r8 = r8.getConnectedServerName();
        r5.setText(r8);
        goto L_0x0038;
    L_0x005d:
        if (r7 != r11) goto L_0x0038;
    L_0x005f:
        r3 = r12.getItem(r13);
        r3 = (com.project.cerberus.mumbleclient.app.DrawerAdapter.DrawerItem) r3;
        r8 = 2131427461; // 0x7f0b0085 float:1.8476539E38 double:1.053065085E-314;
        r5 = r6.findViewById(r8);
        r5 = (android.widget.TextView) r5;
        r8 = 2131427460; // 0x7f0b0084 float:1.8476537E38 double:1.0530650846E-314;
        r2 = r6.findViewById(r8);
        r2 = (android.widget.ImageView) r2;
        r8 = r3.title;
        r5.setText(r8);
        r8 = r3.icon;
        r2.setImageResource(r8);
        r0 = r12.isEnabled(r13);
        r4 = r5.getCurrentTextColor();
        r8 = 16777215; // 0xffffff float:2.3509886E-38 double:8.2890456E-317;
        r4 = r4 & r8;
        if (r0 == 0) goto L_0x009b;
    L_0x008f:
        r8 = -16777216; // 0xffffffffff000000 float:-1.7014118E38 double:NaN;
    L_0x0091:
        r4 = r4 | r8;
        r5.setTextColor(r4);
        r8 = android.graphics.PorterDuff.Mode.MULTIPLY;
        r2.setColorFilter(r4, r8);
        goto L_0x0038;
    L_0x009b:
        r8 = 1426063360; // 0x55000000 float:8.796093E12 double:7.04568915E-315;
        goto L_0x0091;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.project.cerberus.mumbleclient.app.DrawerAdapter.getView(int, android.view.View, android.view.ViewGroup):android.view.View");
    }

    public long getItemId(int position) {
        return (long) ((DrawerRow) getItem(position)).id;
    }

    public DrawerRow getItemWithId(int id) {
        for (int x = 0; x < getCount(); x++) {
            DrawerRow row = (DrawerRow) getItem(x);
            if (row.id == id) {
                return row;
            }
        }
        return null;
    }

    public boolean isEnabled(int position) {
        if (getItemViewType(position) != 1) {
            return false;
        }
        switch ((int) getItemId(position)) {
            case 1:
            case 2:
            case 3:
            case 4:
                return this.mProvider.isConnected();
            default:
                return true;
        }
    }

    public int getItemViewType(int position) {
        Object item = getItem(position);
        if (item instanceof DrawerHeader) {
            return 0;
        }
        return item instanceof DrawerItem ? 1 : 1;
    }

    public int getViewTypeCount() {
        return 2;
    }
}
