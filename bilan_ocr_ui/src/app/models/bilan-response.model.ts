import { Bilan } from './bilan.model';

export interface BilanResponse extends Bilan {
  createdAt: string;
  lastModifiedAt: string;
  publisher: string;
  modifier: string;
}
