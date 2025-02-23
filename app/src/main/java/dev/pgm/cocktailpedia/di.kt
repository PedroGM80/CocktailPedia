package dev.pgm.cocktailpedia

import android.app.Application
import android.content.Context
import androidx.room.Room
import dev.pgm.cocktailpedia.framework.dataBase.COCKTAIL_DATABASE
import dev.pgm.cocktailpedia.framework.dataBase.CocktailDatabase
import dev.pgm.cocktailpedia.ui.screen.ScreenModule
import dev.pgm.data.DataModule
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
        ctx, CocktailDatabase::class.java, COCKTAIL_DATABASE
    ).build()

    @Single
    fun cocktailDao(db: CocktailDatabase) = db.cocktailDao()
}

@Single
fun provideCocktailLocalDataSource(context: Context): CocktailLocalDataSource {
    return CocktailLocalDataSource(context)
}

fun Application.initDI() {
    startKoin {
        androidLogger(Level.ERROR)
        androidContext(this@initDI)
        modules(AppModule().module, DataModule().module, UseCasesModule().module, ScreenModule().module)
    }
}



