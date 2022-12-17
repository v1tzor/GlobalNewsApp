package ru.aleshin.module_injector

/**
 * @author Stanislav Aleshin on 06.10.2022.
 */
interface ComponentHolder<A : BaseFeatureApi, D : BaseFeatureDependencies> {

    fun initComponent(dependencies: D)

    fun fetchApi(): A

    fun reset()
}
