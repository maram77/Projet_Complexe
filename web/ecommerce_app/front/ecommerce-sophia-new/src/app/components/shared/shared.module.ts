import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from "@angular/router";
import { FlexLayoutModule } from '@angular/flex-layout';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatButtonModule} from '@angular/material/button';
import {MatMenuModule} from '@angular/material/menu';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatSelectModule} from '@angular/material/select';
import {MatTabsModule} from '@angular/material/tabs';
import {MatChipsModule} from '@angular/material/chips';
import {MatIconModule} from '@angular/material/icon';
import {MatCardModule} from '@angular/material/card';
import {MatExpansionModule} from '@angular/material/expansion';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatDividerModule} from '@angular/material/divider';
import {MatRadioModule} from '@angular/material/radio';
import {MatListModule} from '@angular/material/list';
import {MatSliderModule} from '@angular/material/slider';
import {MatInputModule} from '@angular/material/input';
import {MatDialogModule} from '@angular/material/dialog';
import { MatTableModule } from '@angular/material';
import { FooterComponent } from './footer/footer.component';
import { MenuComponent } from './menu/menu.component';
import { OrderByPipe } from './pipes/order-by.pipe';
import { CartService } from './services/cart.service';
import { SidebarComponent } from './sidebar/sidebar.component';
import { BannersComponent } from './banners/banners.component';
import { HeaderComponent } from './header/header.component';
import { ShoppingWidgetsComponent } from './shopping-widgets/shopping-widgets.component';
import { BannersFourComponent } from './banners-four/banners-four.component';
import { BlogSectionComponent } from './blog-section/blog-section.component';
import { BannerPromotionComponent } from './banner-promotion/banner-promotion.component';
import { CategoriesMenuComponent } from './categories-menu/categories-menu.component';
import { CategoriesSectionComponent } from './categories-section/categories-section.component';



@NgModule({
  declarations: [
    FooterComponent,
    MenuComponent,
    SidebarComponent,
    OrderByPipe,
    BannersComponent,
    HeaderComponent,
    ShoppingWidgetsComponent,
    BannersFourComponent,
    BlogSectionComponent,
    BannerPromotionComponent,
    CategoriesMenuComponent,
    CategoriesSectionComponent,
  ],
  imports: [
    CommonModule,
    RouterModule,
    MatButtonModule,
    MatSnackBarModule,
    MatToolbarModule,
    MatListModule,
    MatSliderModule,
    MatExpansionModule,
    MatMenuModule,
    MatTableModule,
    MatRadioModule,
    MatDialogModule,
    MatChipsModule,
    MatInputModule,
    MatIconModule,
    MatSidenavModule,
    MatSelectModule,
    MatTabsModule,
    MatDividerModule,
    MatCardModule,
    FlexLayoutModule,
  ],
  exports: [
    CommonModule,
    MatButtonModule,
    MatSnackBarModule,
    MatToolbarModule,
    MatListModule,
    MatExpansionModule,
    MatMenuModule,
    MatTableModule,
    MatSliderModule,
    MatRadioModule,
    MatDialogModule,
    MatChipsModule,
    MatInputModule,
    MatIconModule,
    MatSidenavModule,
    MatSelectModule,
    MatTabsModule,
    MatDividerModule,
    MatCardModule,
    OrderByPipe,
    FooterComponent,
    MenuComponent,
    SidebarComponent,
    BannersComponent,
    FlexLayoutModule,
    HeaderComponent,
    ShoppingWidgetsComponent,
    BannersFourComponent,
    BlogSectionComponent,
    BannerPromotionComponent,
    CategoriesMenuComponent,
    CategoriesSectionComponent,

  ],
  providers: [ ]
})
export class SharedModule {}
