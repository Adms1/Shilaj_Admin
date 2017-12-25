package anandniketan.com.shilajadmin.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by awidiyadew on 15/09/16.
 */
public class CustomDataProvider {

    private static final int MAX_LEVELS = 3;

    private static final int LEVEL_1 = 1;
    private static final int LEVEL_2 = 2;
    private static final int LEVEL_3 = 3;

    private static List<BaseItem> mMenu = new ArrayList<>();

    public static List<BaseItem> getInitialItems() {
        //return getSubItems(new GroupItem("root"));

        List<BaseItem> rootMenu = new ArrayList<>();

        /*
        * ITEM = TANPA CHILD
        * GROUPITEM = DENGAN CHILD
        * */
        rootMenu.add(new Item("Home"));
        rootMenu.add(new Item("Student"));
        rootMenu.add(new Item("Staff"));
        rootMenu.add(new Item("Account"));
        rootMenu.add(new Item("Transport"));
        rootMenu.add(new Item("HR"));
        rootMenu.add(new GroupItem("Other"));
        rootMenu.add(new Item("Logout"));

        return rootMenu;
    }

    public static List<BaseItem> getSubItems(BaseItem baseItem) {

        List<BaseItem> result = new ArrayList<>();
        int level = ((GroupItem) baseItem).getLevel() + 1;
        String menuItem = baseItem.getName();

        if (!(baseItem instanceof GroupItem)) {
            throw new IllegalArgumentException("GroupItem required");
        }

        GroupItem groupItem = (GroupItem)baseItem;
        if(groupItem.getLevel() >= MAX_LEVELS){
            return null;
        }

        /*
        * HANYA UNTUK GROUP-ITEM
        * */
        switch (level){
            case LEVEL_1 :
                switch (menuItem.toUpperCase()){
                    case "Other" :
                        result = getListOther();
                        break;
                }
                break;
        }

        return result;
    }
    private static List<BaseItem> getListOther(){

        List<BaseItem> list = new ArrayList<>();

        list.add(new Item("Student Absent"));
        list.add(new Item("Bulk SMS"));
        list.add(new Item("Single SMS"));
        list.add(new Item("Employee SMS"));
        list.add(new Item("Summary"));
        list.add(new Item("Holiday"));
        list.add(new Item("PTM"));
        list.add(new Item("Activity Logging"));
        list.add(new Item("Announcement"));
        list.add(new Item("Quick Email"));

        return list;
    }
    public static boolean isExpandable(BaseItem baseItem) {
        return baseItem instanceof GroupItem;
    }





}
