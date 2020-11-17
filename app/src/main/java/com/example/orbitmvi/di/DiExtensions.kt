package com.example.orbitmvi.di

import android.os.Build
import android.util.ArraySet
import androidx.annotation.RequiresApi
//
//inline fun <reified T> get(TAG: String? = null): T
//{
//    return  if (TAG !=null)
//        Modules.modulesWithTag.find {
//            it.first == TAG
//        } as T
//    else
//        Modules.currentModules().find {
//            it is T
//        } as T
//}
//
//inline fun <reified T> inject(TAG: TAG? = null): Lazy<T>
//        = lazy {
//    if (TAG !=null)
//        Modules.modulesWithTag.find {
//            it.first == TAG
//        }?.second as T
//    else
//        Modules.currentModules().find {
//             it is T
//         } as T
//}
//inline fun <reified T> factory(TAG: String? = null, scope: ArrayList<Any>.()-> T) {
//    val module = scope.invoke(Modules.modules)
//    if(TAG != null)
//        Modules.modulesWithTag.addWithTag(Pair(TAG, module as Any))
//    else
//        Modules.modules.add(module as Any)
//}
//
//fun initDi( scope:  ModulesIntegration<Any>.() -> Any ){
//    scope.invoke(Modules.modules)
//}
//
//
//class ModulesIntegration<T> : ArrayList<T>(){
//    private val setOfModules = mutableSetOf<T>()
//    override fun add(element: T): Boolean {
//            if(setOfModules.contains(element))
//                throw Exception("Module already defined")
//
//            setOfModules.add(element)
//            return super.add(element)
//    }
//}
//
//class ModulesTagIntegration<T> : ArrayList<T>(){
//    fun addWithTag(element: T): Boolean{
//        if (element !is Pair<*,*>) return false
//        map {iterator->
//            (iterator as? Pair<TAG, *>)?.let{
//                if (iterator.first == element.first)
//                    throw Exception("Module TAG already defined")
//            }
//        }
//        return super.add(element as T)
//    }
//}