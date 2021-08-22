import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { AuthModule } from './auth/auth.module';
import { AccountService } from './account/account.service';
import { AccountModule } from './account/account.module';

@Module({
  imports: [AuthModule, AccountModule],
  controllers: [AppController],
  providers: [AppService, AccountService],
})
export class AppModule {}
