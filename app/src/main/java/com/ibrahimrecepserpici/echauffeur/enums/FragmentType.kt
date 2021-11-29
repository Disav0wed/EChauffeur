package com.ibrahimrecepserpici.echauffeur.enums

import com.ibrahimrecepserpici.echauffeur.R

/**
 * Enum class holding navigation information for fragments
 */
enum class FragmentType: IFragmentNav {
    MAP{
        override fun getMenuItemId() = R.id.bottom_nav_menu_item_map_fragment
        override fun getFragmentNavigationAction() = R.id.action_global_mapFragment
    },
    LIST{
        override fun getMenuItemId() = R.id.bottom_nav_menu_item_list_fragment
        override fun getFragmentNavigationAction() = R.id.action_global_listFragment
    }
}