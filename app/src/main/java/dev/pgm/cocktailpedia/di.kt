package dev.pgm.cocktailpedia

import android.app.Application
import android.content.Context
import androidx.room.Room
import dev.campi.datalibandroid.CocktailServerDataSource
import dev.campi.datalibandroid.ICocktailLocalDataSourceImpl
import dev.campi.datalibandroid.data.DataModule
import dev.campi.datalibandroid.framework.dataBase.COCKTAIL_DATABASE
import dev.campi.datalibandroid.framework.dataBase.CocktailDatabase
import dev.pgm.cocktailpedia.ui.screen.ScreenModule
import dev.pgm.domain.ICocktailLocalDataSource
import dev.pgm.domain.ICocktailRemoteDataSource
import dev.pgm.usecases.UseCasesModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.ksp.generated.module


@Module
@ComponentScan
class AppModule {

    @Single
    fun CocktailDatabase(ctx: Context) = Room.databaseBuilder(
        ctx, CocktailDatabase::class.java,
        COCKTAIL_DATABASE
    ).build()

    @Single
    fun cocktailDao(db: CocktailDatabase) = db.cocktailDao()
}

@Single
fun provideCocktailLocalDataSource(context: Context): ICocktailLocalDataSource {
    return ICocktailLocalDataSourceImpl(context)
}

@Single
fun provideCocktailRemoteDataSource(): ICocktailRemoteDataSource {
    return  CocktailServerDataSource()
}

fun Application.initDI() {
    startKoin {
        androidLogger(Level.ERROR)
        androidContext(this@initDI)
        modules(
            AppModule().module,
            DataModule().module,
            UseCasesModule().module,
            ScreenModule().module
        )
    }
}



