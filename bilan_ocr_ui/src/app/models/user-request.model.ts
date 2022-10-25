import { UserDto } from './user-dto.model';

export interface UserRequest extends UserDto {
  password: string;
}
