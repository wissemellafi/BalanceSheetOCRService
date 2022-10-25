import { UserDto } from './user-dto.model';

export interface UserResponse extends UserDto {
  id: number;
  createdAt: string;
}
