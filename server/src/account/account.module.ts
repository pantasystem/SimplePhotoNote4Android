import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Account } from './account.entity';
import { AccountService } from './account.service';
import { AccountController } from './account.controller';

@Module({
    providers: [AccountService,],
    imports: [TypeOrmModule.forFeature([Account])],
    controllers: [AccountController],
    exports: [AccountService]
})
export class AccountModule {}
