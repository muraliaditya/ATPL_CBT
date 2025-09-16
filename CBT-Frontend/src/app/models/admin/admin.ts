export interface Contest {
  id: string;
  name: string;
  status: 'ACTIVE' | 'COMPLETED';
  startTime?: string;
  endTime?: string;
  duration?: string;
  eligibility?: string;
}