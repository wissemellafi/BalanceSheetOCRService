import { BilanInfo } from './bilan-info.model';

export interface BilanDocument extends BilanInfo {
  document: File;
}
