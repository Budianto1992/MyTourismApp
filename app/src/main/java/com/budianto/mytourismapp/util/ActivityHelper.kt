package com.budianto.mytourismapp.util

interface AddressableActivity{

    val className: String
    val classMapsFragment: String
}

object ActivityHelper {

    const val PACKAGE_NAME = "com.budianto.mytourismapp"


    object Maps : AddressableActivity{
        override val className = "${PACKAGE_NAME}.maps.detail.MapsDestinationActivity"

        override val classMapsFragment = "${PACKAGE_NAME}.maps.MapsFragment"

        const val EXTRA_MAP = "Map"
    }
}